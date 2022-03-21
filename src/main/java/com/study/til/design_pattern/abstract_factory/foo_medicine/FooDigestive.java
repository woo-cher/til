package com.study.til.design_pattern.abstract_factory.foo_medicine;

import com.study.til.design_pattern.factory.Medicine;

public class FooDigestive implements Medicine {

    @Override
    public void get() {
        System.out.println("Foo 사 소화제 드릴게요~");
    }
}
