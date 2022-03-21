package com.study.til.design_pattern.abstract_factory;

import com.study.til.design_pattern.abstract_factory.bar_medicine.BarCold;
import com.study.til.design_pattern.abstract_factory.bar_medicine.BarDigestive;
import com.study.til.design_pattern.abstract_factory.bar_medicine.BarHeadachePill;
import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;

public class BarMedicineFactory implements AbstractMedicineFactory{

    public Medicine getMedicine(Customer customer) {
        return switch (customer) {
            case COLD -> new BarCold();
            case DIGESTIVE -> new BarDigestive();
            case HEADACHE -> new BarHeadachePill();
        };
    }
}
