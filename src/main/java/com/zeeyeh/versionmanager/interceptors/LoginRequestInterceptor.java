package com.zeeyeh.versionmanager.interceptors;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.zeeyeh.versionmanager.entity.RedisDatabaseType;
import com.zeeyeh.versionmanager.utils.RedisFactory;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginRequestInterceptor implements HandlerInterceptor {

    @Value("${token.key:123456}")
    private String tokenKey;
    @Resource
    RedisFactory redisFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || authorization.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        boolean allow;
        JWT jwt = JWTUtil.parseToken(authorization);
        allow = jwt.setKey(tokenKey.getBytes()).verify();
        if (!allow) {
            return allow;
        }
        allow = jwt.validate(0);
        String uid = (String) jwt.getPayload("uid");
        if (uid == null || uid.isEmpty()) {
            allow = false;
        } else {
            allow = Boolean.TRUE.equals(redisFactory.use(RedisDatabaseType.TOKEN).hasKey(uid));
        }
        return allow;
    }
}
