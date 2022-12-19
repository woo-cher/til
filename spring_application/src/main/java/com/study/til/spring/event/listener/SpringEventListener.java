package com.study.til.spring.event.listener;

import com.study.til.spring.event.dto.BarDTO;
import com.study.til.spring.event.dto.FooDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringEventListener {

  @EventListener
  public void process(final FooDTO targetDTO) {
    log.info("who is ? {}", targetDTO.name());
  }

  @EventListener
  public void process(final BarDTO barDTO) {
    log.info("who is ? {}", barDTO.name());
  }
}
