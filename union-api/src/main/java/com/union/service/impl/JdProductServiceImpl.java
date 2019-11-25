package com.union.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.common.SourceEnum;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.ProductDTO;
import com.union.dto.result.ProductDetailDTO;
import com.union.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 京东商品
 */
@Service
public class JdProductServiceImpl implements ProductService {
    @Override
    public List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO) {
        return null;
    }

    @Override
    public ProductDetailDTO queryProductDetail(Page page, ProductParamDTO productParamDTO) {
        return null;
    }

    @Override
    public String getCode() {
        return SourceEnum.jd.getCode();
    }
}
