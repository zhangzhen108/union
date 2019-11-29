package com.union.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;

import java.util.List;

public interface ProductService {
    /**
     * 查询商品列表
     * @return
     */
    List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO);
    /**
     * 获取跳转
     * @param jumpBuyParamDTO
     * @return
     */
    JumpBuyDTO jumpBuy(JumpBuyParamDTO jumpBuyParamDTO);

    String getCode();
}
