package com.union.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.union.dto.param.OrderParamDTO;
import com.union.dto.result.OrderDTO;

public interface OrderService {
    /**
     * 分页
     * @param pageInfo
     * @param orderParamDTO
     * @return
     */
    Page<OrderDTO> queryPage(Page page, OrderParamDTO orderParamDTO);
}
