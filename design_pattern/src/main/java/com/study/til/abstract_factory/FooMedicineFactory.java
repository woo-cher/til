package com.study.til.abstract_factory;

import com.study.til.abstract_factory.foo_medicine.FooCold;
import com.study.til.abstract_factory.foo_medicine.FooDigestive;
import com.study.til.abstract_factory.foo_medicine.FooHeadachePill;
import com.study.til.factory.Customer;
import com.study.til.factory.Medicine;

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
