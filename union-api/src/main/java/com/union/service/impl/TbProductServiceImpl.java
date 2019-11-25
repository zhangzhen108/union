package com.union.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.union.common.BusinessErrorException;
import com.union.common.ErrorEnum;
import com.union.common.SourceEnum;
import com.union.config.TbConfig;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.ProductDTO;
import com.union.dto.result.ProductDetailDTO;
import com.union.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbProductServiceImpl implements ProductService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    TbConfig tbConfig;

    @Override
    public ProductDetailDTO queryProductDetail(Page page, ProductParamDTO productParamDTO) {

        return null;
    }

    @Override
    public List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO) {
        try{
           TaobaoClient client = new DefaultTaobaoClient(tbConfig.getServerUrl(), tbConfig.getAppkey(), tbConfig.getSecret());
            TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
            req.setPageSize(page.getSize());
            req.setPageNo(page.getCurrent());
            req.setQ(productParamDTO.getKeyword());
//            req.setMaterialId(17004L);
            req.setHasCoupon(true);
            req.setAdzoneId(183612470L);
            TbkDgMaterialOptionalResponse rsp = client.execute(req);
            List<TbkDgMaterialOptionalResponse.MapData> mapDataList=rsp.getResultList();
            List<ProductDTO> productDTOList=new ArrayList<>();
            for (TbkDgMaterialOptionalResponse.MapData mapData:mapDataList) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setSource(SourceEnum.jdd.getScource());
                productDTO.setName(mapData.getTitle());
                productDTO.setImgUrl(mapData.getPictUrl());
                BigDecimal jddPrice=new BigDecimal(mapData.getZkFinalPrice());
                BigDecimal couponAmount=new BigDecimal(mapData.getCouponAmount());
                productDTO.setPrice(jddPrice.subtract(couponAmount));
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
