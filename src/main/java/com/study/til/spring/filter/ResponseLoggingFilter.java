package com.study.til.spring.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Slf4j
@Order(2)
public class ResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In Response Logging Filter !!");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
