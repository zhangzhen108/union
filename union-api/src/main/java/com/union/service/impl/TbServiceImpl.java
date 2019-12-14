package com.union.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.union.common.*;
import com.union.config.TbConfig;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.ChannelDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;
import com.union.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbServiceImpl extends ProductService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    TbConfig tbConfig;

    @Override
    public JumpBuyDTO jumpBuy(JumpBuyParamDTO jumpBuyParamDTO) {
        try{
            if(jumpBuyParamDTO==null|| StringUtils.isEmpty(jumpBuyParamDTO.getName())||StringUtils.isEmpty(jumpBuyParamDTO.getUrl())){
                throw new BusinessErrorException(ErrorEnum.CHECK_ERROR);
            }
            TaobaoClient client = new DefaultTaobaoClient(tbConfig.getServerUrl(), tbConfig.getAppkey(), tbConfig.getSecret());
            TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
            req.setText(jumpBuyParamDTO.getName());
            req.setUrl("https:".concat(jumpBuyParamDTO.getUrl()));
            req.setLogo(jumpBuyParamDTO.getImgUrl());
            TbkTpwdCreateResponse rsp = client.execute(req);
            JumpBuyDTO jumpBuyDTO=new JumpBuyDTO();
            jumpBuyDTO.setJumType(JumpTypeEnum.COPY.getType());
            jumpBuyDTO.setData(rsp.getBody());
            return jumpBuyDTO;
        }catch (Exception e){
            log.error("调用TbProductServiceImpl#covertTpwd发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }

    @Override
    public List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO) {
        try{
           TaobaoClient client = new DefaultTaobaoClient(tbConfig.getServerUrl(), tbConfig.getAppkey(), tbConfig.getSecret());
            TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
            req.setPageSize(page.getSize());
            req.setPageNo(page.getCurrent());
            req.setQ(productParamDTO.getKeyword());
            req.setCat(String.valueOf(productParamDTO.getCategoryThirdId()));
//            req.setMaterialId(17004L);
            req.setHasCoupon(true);
            req.setAdzoneId(183612470L);
            if(StringUtils.isNotEmpty(productParamDTO.getSortFiled())){
                if(productParamDTO.getSort()==null){
                    productParamDTO.setSort(SortEnum.DESC.getSort());
                }
                String sort=null;
                if(productParamDTO.getSort()==SortEnum.DESC.getSort()){
                    sort="desc";
                }else{
                    sort="asc";
                }
                req.setSort(productParamDTO.getSortFiled().concat("_").concat(sort));
            }

            List<TbkDgMaterialOptionalResponse.MapData> mapDataList=null;
            int i=0;
            while(true){
                TbkDgMaterialOptionalResponse rsp = client.execute(req);
                mapDataList=rsp.getResultList();
                if(!CollectionUtils.isEmpty(mapDataList)||i>1){
                    break;
                }
                i++;
            }


            List<ProductDTO> productDTOList=new ArrayList<>();
            if(CollectionUtils.isEmpty(mapDataList)){
                return productDTOList;
            }
            for (TbkDgMaterialOptionalResponse.MapData mapData:mapDataList) {
                ProductDTO productDTO = new ProductDTO();
                ChannelDTO channelDTO=new ChannelDTO();
                channelDTO.setCode(SourceEnum.tb.getCode());
                channelDTO.setMsg(SourceEnum.tb.getMsg());
                channelDTO.setId(SourceEnum.tb.getId());
                productDTO.setChannelDTO(channelDTO);
                productDTO.setThirdId(String.valueOf(mapData.getItemId()));
                productDTO.setName(mapData.getTitle());
                productDTO.setImgUrl(mapData.getPictUrl());
                productDTO.setUrl(mapData.getCouponShareUrl());
                if(CollectionUtils.isEmpty(mapData.getSmallImages())){
                    List<String> smallImages=new ArrayList<>();
                    smallImages.add(mapData.getPictUrl());
                    productDTO.setSmallImages(smallImages);
                }else {
                    productDTO.setSmallImages(mapData.getSmallImages());
                }
                productDTO.setSmallImages(mapData.getSmallImages());
                BigDecimal jddPrice=new BigDecimal(mapData.getZkFinalPrice());
                BigDecimal couponAmount=new BigDecimal(mapData.getCouponAmount());
                productDTO.setPrice(jddPrice.subtract(couponAmount));
                productDTO.setDesc(mapData.getItemDescription());
                productDTO.setCouponAmount(couponAmount);
                productDTO.setOriginalPrice(jddPrice);
                productDTOList.add(productDTO);
            }
            return productDTOList;
        }catch (Exception e){
            log.error("调用TbProductServiceImpl#queryList发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }
    private String tpwdCreate(){
        try{
            TaobaoClient client = new DefaultTaobaoClient(tbConfig.getServerUrl(), tbConfig.getAppkey(), tbConfig.getSecret());
            TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
            req.setUserId("123");
            req.setText("长度大于5个字符");
            req.setUrl("https://uland.taobao.com/");
            req.setLogo("https://uland.taobao.com/");
            req.setExt("{}");
            TbkTpwdCreateResponse rsp = client.execute(req);
            return rsp.getBody();
        }catch (Exception e){
            log.error("调用TbProductServiceImpl#tpwdCreate发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }
    @Override
    public String getCode() {
        return SourceEnum.tb.getCode();
    }
}
