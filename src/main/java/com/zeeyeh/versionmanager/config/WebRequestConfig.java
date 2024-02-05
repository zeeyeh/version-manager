package com.zeeyeh.versionmanager.config;

import com.zeeyeh.versionmanager.interceptors.LoginRequestInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebRequestConfig extends WebMvcConfigurationSupport {

    @Resource
    LoginRequestInterceptor loginRequestInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequestInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/member/login", "/member/register");
        super.addInterceptors(registry);
    }
}
