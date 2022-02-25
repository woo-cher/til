package com.study.til.di;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DiTests {
    
    @Autowired
    private ConstructorInjection constructorInjection;

    @Test
    void saveFoo() {
        constructorInjection.save("foo!");
    }
}
