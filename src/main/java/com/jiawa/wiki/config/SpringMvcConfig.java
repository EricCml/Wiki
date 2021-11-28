package com.jiawa.wiki.config;

import com.jiawa.wiki.interceptor.ActionInterceptor;
import com.jiawa.wiki.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;

    @Resource
    ActionInterceptor actionInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截未登录用户
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/test/**",
                        "/redis/**",
                        "/user/login",
                        "/category/all",
                        "/ebook/list",
                        "/doc/all/**",
                        "/doc/find-content/**",
                        "/doc/vote/**",
                        "/ebook-snapshot/**",
                        "/ebook/upload/avatar",
                        "/file/**"
                );

        // 拦截非管理员用户
        registry.addInterceptor(actionInterceptor)
                .addPathPatterns(
                        "/*/save",
                        "/*/delete/**",
                        "/*/reset-password");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:F:/Program/JavaProjects/SpringProjects/wiki/web/dist/image/");
    }
}
