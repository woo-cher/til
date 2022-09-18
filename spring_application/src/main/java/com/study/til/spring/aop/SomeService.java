package com.study.til.spring.aop;

import com.study.til.spring_core.domain.Bar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SomeService {

    public void saveSomeObject() {
        log.info("saved : {}", new Bar());
    }

    @LoggingAspect
    public void doSomething() {
        log.info("do something!!");
    }

    public String getSomeMessage() {
        log.info("will return : some message");
        return "some message";
    }
}
