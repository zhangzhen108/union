package com.union.config;

import com.alibaba.fastjson.JSON;
import com.union.common.ErrorEnum;
import com.union.common.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * token无效错误重写
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Map<String, Object> map = new HashMap<String, Object>();
        Throwable cause = authException.getCause();

        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if(cause instanceof InvalidTokenException) {
                response.getWriter().write(JSON.toJSONString(R.creatR(ErrorEnum.TOKEN_FAILURE))
                );
            }else{
                response.getWriter().write(JSON.toJSONString(R.creatR(ErrorEnum.TOKEN_MISS)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
