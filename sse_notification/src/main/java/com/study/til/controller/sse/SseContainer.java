package com.study.til.controller.sse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
public class SseContainer {
  private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

  public SseEmitter add(final SseEmitter emitter) {
    this.emitters.add(emitter);

    emitter.onCompletion(
        () -> {
          log.info("onCompletion callback");
          this.emitters.remove(emitter);
        });
    emitter.onTimeout(
        () -> {
          log.info("onTimeout callback");
          emitter.complete();
        });
    emitter.onError(e -> log.info("onError callback, {}", e));

    return emitter;
  }

  public void ad() {
    emitters.forEach(emitter -> {
      try {
        emitter.send(SseEmitter.event()
            .name("ad")
            .data("광고 이벤트"));
      } catch (IOException e) {
        emitters.remove(emitter);
      }
    });
  }
}
