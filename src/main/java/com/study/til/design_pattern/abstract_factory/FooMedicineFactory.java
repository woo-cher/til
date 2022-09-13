package com.study.til.design_pattern.abstract_factory;

import com.study.til.design_pattern.abstract_factory.foo_medicine.FooCold;
import com.study.til.design_pattern.abstract_factory.foo_medicine.FooDigestive;
import com.study.til.design_pattern.abstract_factory.foo_medicine.FooHeadachePill;
import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;

public class FooMedicineFactory implements AbstractMedicineFactory{

    public Medicine getMedicine(Customer customer) {
        switch (customer) {
            case COLD:
                return new FooCold();
            case DIGESTIVE:
                return new FooDigestive();
            default:
                return new FooHeadachePill();
        }
    }
}
