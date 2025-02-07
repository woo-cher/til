package com.study.til.spring.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class GeneralPurposeInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("In preHandle method of GeneralPurposeInterceptor");
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    log.info("In postHandle method of GeneralPurposeInterceptor");
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    log.info("In afterCompletion of GeneralPurposeInterceptor");
  }
}
