package com.study.til.spring.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new GeneralPurposeInterceptor()).order(1);
    registry
        .addInterceptor(new SinglePurposeInterceptor())
        .order(2)
        .addPathPatterns("/products/**");
  }
}
