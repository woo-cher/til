package com.study.til;

import com.study.til.filter.SpecificUrlPatternFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:env.properties")
public class TilApplication {

    public static void main(String[] args) {
        SpringApplication.run(TilApplication.class, args);
    }

    @Bean
    FilterRegistrationBean<SpecificUrlPatternFilter> specificUrlPatternFilterFilterRegistrationBean() {
        final FilterRegistrationBean<SpecificUrlPatternFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new SpecificUrlPatternFilter());
        filterRegistrationBean.addUrlPatterns("/specific-url-pattern/*");
        return filterRegistrationBean;
    }
}
