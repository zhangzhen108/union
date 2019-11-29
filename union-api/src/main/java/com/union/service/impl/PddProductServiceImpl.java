package com.union.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPromotionUrlGenerateResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsSearchResponse;
import com.union.common.BusinessErrorException;
import com.union.common.ErrorEnum;
import com.union.common.JumpTypeEnum;
import com.union.common.SourceEnum;
import com.union.config.PddConfig;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.ChannelDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;
import com.union.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 拼多多商品
 */
@Service
public class PddProductServiceImpl implements ProductService {
    @Resource
    PddConfig pddConfig;

    @Override
    public JumpBuyDTO jumpBuy(JumpBuyParamDTO jumpBuyParamDTO) {
        try{
            PopClient client = new PopHttpClient(pddConfig.getClientId(), pddConfig.getClientSecret());
            PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
            request.setPId("9431971_122301546");
            request.setGenerateWeApp(true);
            List<Long> goodsIdList = new ArrayList<Long>();
            goodsIdList.add(jumpBuyParamDTO.getId());
            request.setGoodsIdList(goodsIdList);
            PddDdkGoodsPromotionUrlGenerateResponse response = client.syncInvoke(request);
            PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponse goodsPromotionUrlGenerateResponse=response.getGoodsPromotionUrlGenerateResponse();
            List<PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponseGoodsPromotionUrlListItem> goodsPromotionUrlList=goodsPromotionUrlGenerateResponse.getGoodsPromotionUrlList();
            if(CollectionUtils.isEmpty(goodsPromotionUrlList)){
                throw new BusinessErrorException(ErrorEnum.PRODUCT_NO);
            }
            PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponseGoodsPromotionUrlListItemWeAppInfo weAppInfo=goodsPromotionUrlList.get(0).getWeAppInfo();
            if(weAppInfo==null){
                throw new BusinessErrorException(ErrorEnum.PRODUCT_NO);
            }
            JumpBuyDTO jumpBuyDTO=new JumpBuyDTO();
            jumpBuyDTO.setJumType(JumpTypeEnum.JUMP.getType());
            jumpBuyDTO.setAppid(weAppInfo.getAppId());
            jumpBuyDTO.setPath(weAppInfo.getPagePath());
            return jumpBuyDTO;
        }catch (Exception e){
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }

    @Override
    public List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO) {
        try {
            PopClient client = new PopHttpClient(pddConfig.getClientId(), pddConfig.getClientSecret());
            PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
            request.setKeyword(productParamDTO.getKeyword());
            //request.setOptId(0L);
            request.setPage((int) page.getCurrent());
            request.setPageSize((int) page.getSize());
            request.setSortType(productParamDTO.getSortType());
            request.setWithCoupon(true);
            request.setCatId(productParamDTO.getCategoryId());
            PddDdkGoodsSearchResponse response = client.syncInvoke(request);
            PddDdkGoodsSearchResponse.GoodsSearchResponse goodsSearchResponse = response.getGoodsSearchResponse();
            List<PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem> goodsList = goodsSearchResponse.getGoodsList();
            List<ProductDTO> productDTOList = new ArrayList<>();
            if(CollectionUtils.isEmpty(goodsList)){
                return productDTOList;
            }
            goodsList.forEach(good -> {
                        ProductDTO productDTO = new ProductDTO();
                productDTO.setThirdId(good.getGoodsId());
                ChannelDTO channelDTO=new ChannelDTO();
                channelDTO.setCode(SourceEnum.jdd.getCode());
                channelDTO.setMsg(SourceEnum.jdd.getMsg());
                        productDTO.setChannelDTO(channelDTO);
                productDTO.setDesc(good.getGoodsDesc());
                        productDTO.setName(good.getGoodsName());
                        productDTO.setImgUrl(good.getGoodsImageUrl());
                        if(CollectionUtils.isEmpty(good.getGoodsGalleryUrls())){
                            List<String> smallImages=new ArrayList<>();
                            smallImages.add(good.getGoodsImageUrl());
                            productDTO.setSmallImages(smallImages);
                        }else {
                            productDTO.setSmallImages(good.getGoodsGalleryUrls());
                        }

                productDTO.setCouponAmount(new BigDecimal(good.getCouponDiscount()));
                        BigDecimal price = new BigDecimal((good.getMinGroupPrice() - good.getCouponDiscount()));
                productDTO.setOriginalPrice(new BigDecimal(good.getMinGroupPrice()));
                        productDTO.setPrice(price);
                productDTOList.add(productDTO);
                    }
            );
            return productDTOList;
        } catch (Exception e) {
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
    }

    @Override
    public String getCode() {
        return SourceEnum.jdd.getCode();
    }
}
