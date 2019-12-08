package com.union.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.common.ErrorEnum;
import com.union.common.R;
import com.union.dto.param.CategoryParamDTO;
import com.union.dto.param.OrderParamDTO;
import com.union.dto.result.CategoryDTO;
import com.union.dto.result.OrderDTO;
import com.union.service.CategoryService;
import com.union.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

    @Resource
    OrderService orderService;
    @GetMapping("queryPage")
    public R queryPage(OrderParamDTO orderParamDTO, Page page){
        Page<OrderDTO> orderDTOPage= orderService.queryPage(page,orderParamDTO);
        return R.creatR(orderDTOPage, ErrorEnum.SUCCESS);
    }
}
