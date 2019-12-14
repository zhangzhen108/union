package com.union.controller;

import com.union.common.BusinessErrorException;
import com.union.common.ErrorEnum;
import com.union.common.R;
import com.union.common.SourceEnum;
import com.union.config.MgjConfig;
import com.union.dto.param.JumpBuyParamDTO;
import com.union.dto.result.JumpBuyDTO;
import com.union.dto.result.ProductDTO;
import com.union.service.ProductService;
import com.union.service.impl.ProductInit;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/redirectController/")
public class RedirectController {

    @GetMapping("mgj/login")
    public R mgjLogin(@RequestParam("code")String code,@RequestParam("state")String state){
        ProductService productService=ProductInit.PRODUCT_MAP.get(SourceEnum.mgj.getCode());
        if(productService==null){
            throw new BusinessErrorException(ErrorEnum.SOURCE_NO_HAVE);
        }
        productService.login(code,state);
        return R.ok();
    }
}
