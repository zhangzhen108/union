package com.union.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.union.common.BusinessErrorException;
import com.union.common.ErrorEnum;
import com.union.common.JumpTypeEnum;
import com.union.common.SourceEnum;
import com.union.config.JdConfig;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.ChannelDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;
import com.union.service.ProductService;
import jd.union.open.goods.query.request.GoodsReq;
import jd.union.open.goods.query.request.UnionOpenGoodsQueryRequest;
import jd.union.open.goods.query.response.Coupon;
import jd.union.open.goods.query.response.GoodsResp;
import jd.union.open.goods.query.response.UnionOpenGoodsQueryResponse;
import jd.union.open.goods.query.response.UrlInfo;
import jd.union.open.promotion.byunionid.get.request.PromotionCodeReq;
import jd.union.open.promotion.byunionid.get.request.UnionOpenPromotionByunionidGetRequest;
import jd.union.open.promotion.byunionid.get.response.PromotionCodeResp;
import jd.union.open.promotion.byunionid.get.response.UnionOpenPromotionByunionidGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 京东商品
 */
@Service
public class JdServiceImpl extends ProductService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    JdConfig jdConfig;
    @Override
    public JumpBuyDTO jumpBuy(JumpBuyParamDTO jumpBuyParamDTO) {
        try{
            JdClient client=new DefaultJdClient(jdConfig.getServerUrl(),null,jdConfig.getAppKey(),jdConfig.getAppSecret());
            UnionOpenPromotionByunionidGetRequest request=new UnionOpenPromotionByunionidGetRequest();
            PromotionCodeReq promotionCodeReq=new PromotionCodeReq();
            promotionCodeReq.setMaterialId(jumpBuyParamDTO.getUrl());
            promotionCodeReq.setUnionId(1001906380L);
            promotionCodeReq.setCouponUrl(jumpBuyParamDTO.getCouponUrl());
            request.setPromotionCodeReq(promotionCodeReq);
            UnionOpenPromotionByunionidGetResponse response=client.execute(request);
            PromotionCodeResp promotionCodeResp=response.getData();
            JumpBuyDTO jumpBuyDTO = new JumpBuyDTO();
            jumpBuyDTO.setJumType(JumpTypeEnum.JUMP.getType());
            jumpBuyDTO.setAppid("wx13e41a437b8a1d2e");
            jumpBuyDTO.setPath("/pages/product/product?wareId=".concat(jumpBuyParamDTO.getProductId()).concat("&spreadUrl=").concat(promotionCodeResp.getShortURL()).concat("&customerinfo=20190115ypxp"));
            return jumpBuyDTO;
        }catch (Exception e){
            log.error("调用JdProductServiceImpl#jumpBuy发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }

    @Override
    public List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO) {
        try{
            JdClient client=new DefaultJdClient(jdConfig.getServerUrl(),null,jdConfig.getAppKey(),jdConfig.getAppSecret());
            UnionOpenGoodsQueryRequest request=new UnionOpenGoodsQueryRequest();
            GoodsReq goodsReqDTO=new GoodsReq();
            goodsReqDTO.setPageSize(Long.valueOf(page.getSize()).intValue());
            goodsReqDTO.setPageIndex(Long.valueOf(page.getCurrent()).intValue());
            if(StringUtils.isEmpty(productParamDTO)&&StringUtils.isEmpty(productParamDTO.getCategoryThirdId())){
                productParamDTO.setCategoryThirdId(1L);
            }
            goodsReqDTO.setKeyword(productParamDTO.getKeyword());
            goodsReqDTO.setCid3(productParamDTO.getCategoryThirdId());
            goodsReqDTO.setIsCoupon(1);
            request.setGoodsReqDTO(goodsReqDTO);
            UnionOpenGoodsQueryResponse response=client.execute(request);
            GoodsResp[] goodsResps=response.getData();
            List<ProductDTO> productDTOList=new ArrayList<>();
            if(goodsResps==null||goodsResps.length==0){
                return productDTOList;
            }
            for (GoodsResp goodsResp:goodsResps) {
                ProductDTO productDTO = new ProductDTO();
                ChannelDTO channelDTO=new ChannelDTO();
                channelDTO.setCode(SourceEnum.jd.getCode());
                channelDTO.setMsg(SourceEnum.jd.getMsg());
                channelDTO.setId(SourceEnum.jd.getId());
                productDTO.setChannelDTO(channelDTO);
                productDTO.setThirdId(String.valueOf(goodsResp.getSkuId()));
                productDTO.setName(goodsResp.getSkuName());
                productDTO.setImgUrl(goodsResp.getImageInfo().getImageList()[0].getUrl());
                productDTO.setUrl(goodsResp.getMaterialUrl());
                List<String> smallImageUrlList=new ArrayList<>();
                for (UrlInfo urlInfo:goodsResp.getImageInfo().getImageList()) {
                    smallImageUrlList.add(urlInfo.getUrl());
                }
                productDTO.setSmallImages(smallImageUrlList);
                BigDecimal divisor = new BigDecimal(100);

                Coupon[] couponArr=goodsResp.getCouponInfo().getCouponList();
                BigDecimal couponAmount=null;
                for (Coupon coupon:couponArr) {
                    if("1".equals(coupon.getIsBest())){
                        productDTO.setCouponUrl(coupon.getLink());
                        couponAmount=new BigDecimal(coupon.getDiscount());
                    }
                }
                if(couponAmount==null){
                    productDTO.setCouponUrl(couponArr[0].getLink());
                    couponAmount=new BigDecimal(couponArr[0].getDiscount());
                }
                productDTO.setCouponAmount(couponAmount);
                BigDecimal price = new BigDecimal((goodsResp.getPriceInfo().getLowestPrice()));
                productDTO.setOriginalPrice(price);
                productDTO.setPrice(price.subtract(couponAmount));
                productDTOList.add(productDTO);
            }
            return productDTOList;
        }catch (Exception e){
            log.error("调用JdProductServiceImpl#queryList发生异常,异常信息为",e);
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }

    }

    @Override
    public String getCode() {
        return SourceEnum.jd.getCode();
    }
}
