package com.zeeyeh.versionmanager.interceptors;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.versionmanager.annotation.FilterPassword;
import com.zeeyeh.versionmanager.entity.Member;
import com.zeeyeh.versionmanager.entity.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

@RestControllerAdvice
@Slf4j
public class MemberInterceptor implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Response responseBody) {
            Object data = responseBody.getData();
            if (data instanceof Member member) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", member.getId());
                jsonObject.put("uid", member.getUid());
                jsonObject.put("username", member.getUsername());
                jsonObject.put("nickname", member.getNickname());
                jsonObject.put("email", member.getEmail());
                jsonObject.put("avatar", member.getAvatar());
                jsonObject.put("createTime", member.getCreateTime());
                jsonObject.put("updateTime", member.getUpdateTime());
                jsonObject.put("roles", JSONArray.parseArray(member.getRoles()));
                jsonObject.put("status", member.getStatus());
                data = jsonObject;
            }
            responseBody.setData(data);
            return responseBody;
        }
        return body;
    }
}
