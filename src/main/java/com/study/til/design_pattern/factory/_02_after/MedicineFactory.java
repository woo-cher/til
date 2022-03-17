package com.study.til.design_pattern.factory._02_after;

import com.study.til.design_pattern.factory.*;

public class MedicineFactory {

    public Medicine getMedicine(Customer customer) {
        return switch (customer) {
            case COLD -> new Cold();
            case DIGESTIVE -> new Digestive();
            case HEADACHE -> new HeadachePill();
        };
    }
}
