package com.study.til.handler.hello;

import com.study.til.adapter.HelloAdapter;
import com.study.til.adapter.req.HelloReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
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

  public Mono<ServerResponse> doOnNext(final ServerRequest serverRequest) {
    return Mono.just("Foo")
        .doOnNext(name -> { // sub1, 2, 3 모두 동일 쓰레드가 처리
          sub1();
          sub2();
          sub3();
        })
        .flatMap(res -> ServerResponse.ok().body(BodyInserters.fromValue(res)))
        .onErrorResume(Mono::error);
  }

  private void sub1() {
    threadNameLog();
  }
  private void sub2() {
    threadNameLog();
  }
  private void sub3() {
    threadNameLog();
  }
  private void threadNameLog() {
    log.info("Thread : {}", Thread.currentThread().getName());
  }
}
