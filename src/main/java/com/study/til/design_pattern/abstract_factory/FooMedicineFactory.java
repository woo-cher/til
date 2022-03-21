package com.study.til.design_pattern.abstract_factory;

import com.study.til.design_pattern.abstract_factory.foo_medicine.FooCold;
import com.study.til.design_pattern.abstract_factory.foo_medicine.FooDigestive;
import com.study.til.design_pattern.abstract_factory.foo_medicine.FooHeadachePill;
import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;

public class FooMedicineFactory implements AbstractMedicineFactory{

    public Medicine getMedicine(Customer customer) {
        return switch (customer) {
            case COLD -> new FooCold();
            case DIGESTIVE -> new FooDigestive();
            case HEADACHE -> new FooHeadachePill();
        };
    }
}
