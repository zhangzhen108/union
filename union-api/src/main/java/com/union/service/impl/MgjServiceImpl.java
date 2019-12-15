package com.union.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mogujie.openapi.MogujieClient;
import com.mogujie.openapi.request.MgjRequest;
import com.mogujie.openapi.util.HttpClient;
import com.mogujie.openapi.util.SignUtil;
import com.union.common.BusinessErrorException;
import com.union.common.ErrorEnum;
import com.union.common.JumpTypeEnum;
import com.union.common.SourceEnum;
import com.union.config.MgjConfig;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.ChannelDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;
import com.union.service.ProductService;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MgjServiceImpl extends ProductService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    RestTemplate restTemplate;
    @Resource
    MgjConfig mgjConfig;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ProductDTO> index(Page page, ProductParamDTO productParamDTO) {
        productParamDTO.setSortType(12);
        return this.queryList(page,productParamDTO);
    }

    @Override
    public void login(String code, String state) {
        try{
            MultiValueMap<String,String> map=new LinkedMultiValueMap<>();
            map.add("code",code);
            map.add("app_key",mgjConfig.getAppKey());
            map.add("app_secret",mgjConfig.getAppSecret());
            map.add("redirect_uri",mgjConfig.getRedirectUri());
            map.add("grant_type","authorization_code");
            map.add("state","123");
            //String json=HttpClient.doPost(mgjConfig.getTokenUrl(),map,"UTF-8",1000,1000,new HashMap<>());
            //Map<String,String> result=JSONObject.parseObject(json,Map.class);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(mgjConfig.getTokenUrl(), map, Map.class);
            Map<String,Object> result=responseEntity.getBody();
            redisTemplate.opsForValue().set("mgj_access_token",result.get("access_token"),((Integer)result.get("access_expires_in")).longValue(), TimeUnit.SECONDS);
            redisTemplate.opsForValue().set("mgj_refresh_token",result.get("refresh_token"),((Integer)result.get("refresh_expires_in")).longValue(),TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("调用MgjServiceImpl#login发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }

    }
    private void refreshToken(){
        String refreshToken=(String)redisTemplate.opsForValue().get("mgj_refresh_token");
        if(StringUtils.isEmpty(refreshToken)){
            log.warn("mgj-token失效,请重新获取");
            throw new BusinessErrorException(ErrorEnum.TOKEN_MISS);
        }
        MultiValueMap<String,String> map=new LinkedMultiValueMap<>();
        map.add("refresh_token",refreshToken);
        map.add("app_key",mgjConfig.getAppKey());
        map.add("app_secret",mgjConfig.getAppSecret());
        map.add("grant_type","refresh_token");
        map.add("state","state");
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(mgjConfig.getTokenUrl(), map, Map.class);
        Map<String,Object> result=responseEntity.getBody();
        redisTemplate.opsForValue().set("mgj_access_token",result.get("access_token"),((Integer)result.get("access_expires_in")).longValue(),TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("mgj_refresh_token",result.get("refresh_token"),((Integer)result.get("refresh_expires_in")).longValue(),TimeUnit.SECONDS);
    }
    @Override
    public List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO) {
        try{
            MogujieClient mogujieClient=new MogujieClient(mgjConfig.getAppKey(),mgjConfig.getAppSecret(),mgjConfig.getServerUrl());
            String accessToken=(String)redisTemplate.opsForValue().get("mgj_access_token");
            if(StringUtils.isEmpty(accessToken)){
                refreshToken();
                accessToken=(String)redisTemplate.opsForValue().get("mgj_access_token");
            }
            MultiValueMap<String,String> map=new LinkedMultiValueMap<>();
            map.add("access_token",accessToken);
            map.add("app_key",mgjConfig.getAppKey());
            map.add("method","xiaodian.cpsdata.promitem.get");
            map.add("format","json");
            Map<String,Object> param=new HashMap<>();
            param.put("keyword",productParamDTO.getKeyword());
            param.put("hasCoupon","true");
            param.put("cid",productParamDTO.getCategoryThirdId());
            map.add("promInfoQuery",JSON.toJSONString(param));
            map.add("timestamp",String.valueOf(System.currentTimeMillis()));
            map.add("sign", SignUtil.signRequest(map.toSingleValueMap(),mgjConfig.getAppSecret(),"md5"));
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity("https://openapi.mogujie.com/invoke", map, JSONObject.class);
            JSONObject jsonObject = responseEntity.getBody();
            List<ProductDTO> productDTOList=new ArrayList<>();
            JSONObject result=jsonObject.getJSONObject("result");
            if(result==null){
                return productDTOList;
            }
            JSONObject data=result.getJSONObject("data");
            if(data==null){
                return productDTOList;
            }
            JSONArray items=data.getJSONArray("items");
            if(CollectionUtils.isEmpty(items)){
                return productDTOList;
            }
            for(int i=0;i<items.size();i++){
                //3、把里面的对象转化为JSONObject
                JSONObject item = items.getJSONObject(i);
                ProductDTO productDTO = new ProductDTO();
                productDTO.setName(item.getString("title"));
                ChannelDTO channelDTO=new ChannelDTO();
                channelDTO.setCode(SourceEnum.mgj.getCode());
                channelDTO.setMsg(SourceEnum.mgj.getMsg());
                channelDTO.setId(SourceEnum.mgj.getId());
                productDTO.setChannelDTO(channelDTO);
                productDTO.setThirdId(item.getString("itemId"));
                productDTO.setImgUrl(item.getString("pictUrlForH5"));
                productDTO.setUrl(item.getString("itemUrl"));
                List<String> smallImageUrlList=new ArrayList<>();
                smallImageUrlList.add(productDTO.getImgUrl());
                productDTO.setSmallImages(smallImageUrlList);
                BigDecimal couponAmount=new BigDecimal(item.getString("couponAmount"));
                productDTO.setCouponAmount(couponAmount);
                productDTO.setOriginalPrice(new BigDecimal(item.getString("zkPrice")));
                productDTO.setPrice(new BigDecimal(item.getString("afterCouponPrice")));
                productDTO.setCouponId(item.getString("promid"));
                productDTOList.add(productDTO);
            }
            return productDTOList;
        }catch (Exception e){
            log.error("调用MgjServiceImpl#queryList发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }

    @Override
    public JumpBuyDTO jumpBuy(JumpBuyParamDTO jumpBuyParamDTO) {
        try{
            String accessToken=(String)redisTemplate.opsForValue().get("mgj_access_token");
            if(StringUtils.isEmpty(accessToken)){
                refreshToken();
                accessToken=(String)redisTemplate.opsForValue().get("mgj_access_token");
            }
            MultiValueMap<String,String> map=new LinkedMultiValueMap<>();
            map.add("access_token",accessToken);
            map.add("app_key",mgjConfig.getAppKey());
            map.add("method","xiaodian.cpsdata.wxcode.get");
            map.add("format","json");
            map.add("promInfoQuery",mgjConfig.getAppKey());
            map.add("timestamp",String.valueOf(System.currentTimeMillis()));
            Map<String,String> param=new HashMap<>();
            param.put("itemId",jumpBuyParamDTO.getProductId());
            param.put("promId",jumpBuyParamDTO.getCouponId());
            param.put("uid","1a88ko4");
            param.put("genWxcode","false");
            map.add("wxcodeParam", JSON.toJSONString(param));
            map.add("sign", SignUtil.signRequest(map.toSingleValueMap(),mgjConfig.getAppSecret(),"md5"));
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity("https://openapi.mogujie.com/invoke", map, JSONObject.class);
            JSONObject jsonObject = responseEntity.getBody();
            JumpBuyDTO jumpBuyDTO=new JumpBuyDTO();
            JSONObject result=jsonObject.getJSONObject("result");
            if(result==null){
                return jumpBuyDTO;
            }
            JSONObject data=result.getJSONObject("data");
            if(data==null){
                return jumpBuyDTO;
            }
            jumpBuyDTO.setPath(data.getString("path"));
            jumpBuyDTO.setJumType(JumpTypeEnum.JUMP.getType());
            jumpBuyDTO.setAppid("wxca3957e5474b3670");
            return jumpBuyDTO;
        }catch (Exception e){
            log.error("调用MgjServiceImpl#jumpBuy发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }
    @Override
    public String getCode() {
        return SourceEnum.mgj.getCode();
    }
}
