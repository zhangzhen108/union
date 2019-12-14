package com.union.service.impl;

import com.union.config.MgjConfig;
import com.union.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class MgjLoginServiceImpl implements LoginService {
    @Resource
    RestTemplate restTemplate;
    @Resource
    MgjConfig mgjConfig;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void login(String code, String state) {

    }
}
