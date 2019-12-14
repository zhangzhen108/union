package com.union.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.param.ProductParamDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;

import java.util.List;

public abstract class ProductService {

    public void login(String code,String state){

    }
    /**
     * 查询商品列表
     * @return
     */
    public abstract List<ProductDTO> queryList(Page page, ProductParamDTO productParamDTO);
    /**
     * 获取跳转
     * @param jumpBuyParamDTO
     * @return
     */
    public abstract JumpBuyDTO jumpBuy(JumpBuyParamDTO jumpBuyParamDTO);

    public abstract String getCode();
}
