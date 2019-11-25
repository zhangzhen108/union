package com.union.service.impl;

import com.union.service.ProductService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductInit implements ApplicationContextAware {
    public static final Map<String, ProductService> PRODUCT_MAP=new HashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,ProductService> productServiceMap=applicationContext.getBeansOfType(ProductService.class);
        if(productServiceMap==null){
            return;
        }
        for (String name:productServiceMap.keySet()) {
            ProductService productService=productServiceMap.get(name);
            PRODUCT_MAP.put(productService.getCode(),productService);
        }
    }
}
