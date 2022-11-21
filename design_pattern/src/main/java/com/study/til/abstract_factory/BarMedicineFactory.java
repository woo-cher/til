package com.study.til.abstract_factory;

import com.study.til.abstract_factory.bar_medicine.BarCold;
import com.study.til.abstract_factory.bar_medicine.BarDigestive;
import com.study.til.abstract_factory.bar_medicine.BarHeadachePill;
import com.study.til.factory.Customer;
import com.study.til.factory.Medicine;

public class BarMedicineFactory implements AbstractMedicineFactory {

  public Medicine getMedicine(Customer customer) {
    switch (customer) {
      case COLD:
        return new BarCold();
      case DIGESTIVE:
        return new BarDigestive();
      default:
        return new BarHeadachePill();
    }
  }
}
