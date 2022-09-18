package com.study.til.psa;

import com.study.til.spring.psa.BazIntroductionService;
import com.study.til.spring.psa.FooIntroductionService;
import com.study.til.spring.psa.Introduction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class PsaTests {
    private Introduction introduction; // 추상 클래스 계층

    @Test
    public void introduction() {
        introduction = new BazIntroductionService();
        log.debug("Who is ? {}", introduction.doIntroduction());

        introduction = new FooIntroductionService();
        log.debug("Who is ? {}", introduction.doIntroduction());
    }
}
