package com.study.til.event.listener;

import com.study.til.event.dto.BarDTO;
import com.study.til.event.dto.FooDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringEventListener {

  @EventListener
  public void process(final FooDTO fooDTO) {
    log.info("who is ? {}", fooDTO.name());
  }

  @EventListener
  public void process(final BarDTO barDTO) {
    log.info("who is ? {}", barDTO.name());
  }
}
