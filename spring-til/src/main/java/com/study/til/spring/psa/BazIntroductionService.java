package com.study.til.spring.psa;

public class BazIntroductionService implements Introduction {

    @Override
    public String doIntroduction() {
        return "Hello, I'm Baz";
    }
}
