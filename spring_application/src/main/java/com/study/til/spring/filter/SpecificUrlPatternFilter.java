package com.study.til.spring.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class SpecificUrlPatternFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In Specific Url Pattern Filter !!");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
