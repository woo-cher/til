package com.study.til.design_pattern.abstract_factory.bar_medicine;

import com.study.til.design_pattern.factory.Medicine;

public class BarHeadachePill implements Medicine {

    @Override
    public void get() {
        System.out.println("Bar 사 두통약 드릴게요~");
    }
}
