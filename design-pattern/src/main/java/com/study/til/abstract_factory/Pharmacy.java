package com.study.til.abstract_factory;

import com.study.til.factory.Customer;
import com.study.til.factory.Medicine;

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
