package com.study.til.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.study.til.handler.hello.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HelloRoute {

  @Bean
  public RouterFunction<ServerResponse> hello(HelloHandler handler) {
    return route(GET("/hello"), handler::hello);
  }
}
