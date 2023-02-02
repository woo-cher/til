package com.study.til.handler.hello;

import com.study.til.adapter.HelloAdapter;
import com.study.til.adapter.req.HelloReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HelloHandler {
  private final HelloAdapter<String> helloAdapter;

  @Autowired
  public HelloHandler(HelloAdapter<String> helloAdapter) {
    this.helloAdapter = helloAdapter;
  }

  public Mono<ServerResponse> hello(final ServerRequest serverRequest) {
    return Mono.just(serverRequest.queryParam("name").orElse(""))
        .map(HelloReq::new)
        .map(helloAdapter::handle)
        .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res)))
        .onErrorResume(Mono::error);
  }
}
