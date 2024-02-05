package com.zeeyeh.versionmanager.interceptors;

import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.versionmanager.entity.Member;
import com.zeeyeh.versionmanager.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseInterceptors implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Response responseBody) {
            int code = responseBody.getCode();
            String message = responseBody.getMessage();
            Object data = responseBody.getData();
            if (data instanceof Response) {
                Response responseData = (Response) data;
                Object object = responseData.getData();
                if (object instanceof Member) {
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            jsonObject.put("message", message);
            if (data != null) {
                jsonObject.put("data", data);
            }
            body = jsonObject;
        }
        return body;
    }
}
