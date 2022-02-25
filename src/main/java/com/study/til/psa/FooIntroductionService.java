package com.study.til.psa;

import org.springframework.stereotype.Service;

public class FooIntroductionService implements Introduction {

    @Override
    public String doIntroduction() {
        return "Hello, I'm Foo";
    }
}
