package com.union.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.common.BusinessErrorException;
import com.union.common.ErrorEnum;
import com.union.common.R;
import com.union.common.SourceEnum;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;
import com.union.service.ProductService;
import com.union.service.impl.ProductInit;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productController/")
public class ProductController {
    @GetMapping("index")
    public R index(Page page, ProductParamDTO productParamDTO){
        if(productParamDTO==null){
            throw new BusinessErrorException(ErrorEnum.CHECK_ERROR);
        }
        if(productParamDTO.getChannelCode()==null){
            productParamDTO.setChannelCode(SourceEnum.jdd.getCode());
        }
        if(StringUtils.isEmpty(productParamDTO.getKeyword())){
            productParamDTO.setKeyword("衣服");
        }
        ProductService productService=ProductInit.PRODUCT_MAP.get(productParamDTO.getChannelCode());
        if(productService==null){
            throw new BusinessErrorException(ErrorEnum.SOURCE_NO_HAVE);
        }
        List<ProductDTO>  productDTOList=productService.queryList(page,productParamDTO);
        return R.creatR(productDTOList, ErrorEnum.SUCCESS);
    }
    @GetMapping("queryList")
    public R queryList(Page page, ProductParamDTO productParamDTO){
        if(productParamDTO==null|| StringUtils.isEmpty(productParamDTO.getChannelCode())){
            throw new BusinessErrorException(ErrorEnum.CHECK_ERROR);
        }
        ProductService productService=ProductInit.PRODUCT_MAP.get(productParamDTO.getChannelCode());
        if(productService==null){
            throw new BusinessErrorException(ErrorEnum.SOURCE_NO_HAVE);
        }
        List<ProductDTO>  productDTOList=productService.queryList(page,productParamDTO);
        return R.creatR(productDTOList, ErrorEnum.SUCCESS);
    }
    @GetMapping("buyNow")
    public R buyNow(JumpBuyParamDTO jumpBuyParamDTO){
        if(jumpBuyParamDTO==null){
            throw new BusinessErrorException(ErrorEnum.CHECK_ERROR);
        }
        ProductService productService=ProductInit.PRODUCT_MAP.get(jumpBuyParamDTO.getChannelCode());
        if(productService==null){
            throw new BusinessErrorException(ErrorEnum.SOURCE_NO_HAVE);
        }
       JumpBuyDTO jumpBuyDTO=productService.jumpBuy(jumpBuyParamDTO);
        return R.creatR(jumpBuyDTO, ErrorEnum.SUCCESS);
    }
}
