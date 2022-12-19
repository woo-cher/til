package com.study.til.controller.sse;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@CrossOrigin(originPatterns = "http://localhost:8080/**")
@RequiredArgsConstructor
public class SseController {
  private final static Long SSE_TIMEOUT = 60 * 1000L;
  private final SseContainer sseContainer;

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> connect() {
    SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
    sseContainer.add(emitter);

    try {
      emitter.send(SseEmitter.event()
          .name("connect")
          .data("connected!"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return ResponseEntity.ok(emitter);
  }

  @GetMapping(value = "/ad")
  public ResponseEntity<Void> count() {
    sseContainer.ad();
    return ResponseEntity.ok().build();
  }
}
