package com.m.demo.config;
import org.springframework.context.annotation.*;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import java.util.List;

/**
 * author:M
 * describe:
 * date:2020/3/6 11:23*/


@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        System.out.println("启用拦截！");
//        registry.addInterceptor(globalInterceptor()).addPathPatterns("/**");//启用gateway网关拦截
//        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");//登录认证拦截
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        System.out.println("进入addArgumentResolvers");
        argumentResolvers.add(new ParameterHandler());
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public GlobalInterceptor globalInterceptor() {
        return new GlobalInterceptor();
    }
}
