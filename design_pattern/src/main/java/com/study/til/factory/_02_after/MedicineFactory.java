package com.study.til.factory._02_after;

import com.study.til.factory.*;

public class MedicineFactory implements Factory {

    public Medicine getMedicine(Customer customer) {
        switch (customer) {
            case COLD:
                return new Cold();
            case DIGESTIVE:
                return new Digestive();
            default:
                return new HeadachePill();
        }
    }
}
