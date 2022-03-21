package com.study.til.design_pattern.abstract_factory;

import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;

public class Pharmacy {
    public static void main(String[] args) {
        // Foo 사 약 제공
        AbstractMedicineFactory factory = new FooMedicineFactory();

        Medicine medicine = factory.getMedicine(Customer.COLD);
        medicine.get();

        medicine = factory.getMedicine(Customer.DIGESTIVE);
        medicine.get();

        medicine = factory.getMedicine(Customer.HEADACHE);
        medicine.get();

        // Bar 사 약 제공
        factory = new BarMedicineFactory();

        medicine = factory.getMedicine(Customer.COLD);
        medicine.get();

        medicine = factory.getMedicine(Customer.DIGESTIVE);
        medicine.get();

        medicine = factory.getMedicine(Customer.HEADACHE);
        medicine.get();
    }
}
