package com.union.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.ProductDTO;
import com.union.dto.result.ProductDetailDTO;

import java.util.List;

public interface ProductService {
    /**
     * 查询商品列表
     * @return
     */
    List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO);

    /**
     * 查询单个商品
     * @param page
     * @param productParamDTO
     * @return
     */
    ProductDetailDTO queryProductDetail(Page page, ProductParamDTO productParamDTO);
    String getCode();
}
