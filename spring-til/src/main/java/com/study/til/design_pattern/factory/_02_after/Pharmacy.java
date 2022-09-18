package com.study.til.design_pattern.factory._02_after;

import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;

public class Pharmacy {

    public static void main(String[] args) {
        MedicineFactory factory = new MedicineFactory();

        Medicine medicine = factory.getMedicine(Customer.COLD);
        medicine.get();

        medicine = factory.getMedicine(Customer.DIGESTIVE);
        medicine.get();

        medicine = factory.getMedicine(Customer.HEADACHE);
        medicine.get();
    }
}
