package com.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.union.common.CommonStatusEnum;
import com.union.domain.OrderDO;
import com.union.dto.param.OrderParamDTO;
import com.union.dto.result.OrderDTO;
import com.union.mapper.OrderDOMapper;
import com.union.service.OrderService;
import com.union.trans.OrderTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDOMapper, OrderDO> implements OrderService {
    @Resource
    OrderTrans orderTrans;
    @Override
    public Page<OrderDTO> queryPage(Page page, @RequestParam(required=false) OrderParamDTO orderParamDTO) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status", CommonStatusEnum.enable.getStatus());
        Page<OrderDO> orderDOPage=(Page<OrderDO>) super.page(page,queryWrapper);
        Page<OrderDTO> orderDTOPage=orderTrans.toDto(orderDOPage);
        return orderDTOPage;
    }
}
