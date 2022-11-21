package com.study.til.abstract_factory;

import com.study.til.abstract_factory.bar_medicine.BarCold;
import com.study.til.abstract_factory.bar_medicine.BarDigestive;
import com.study.til.abstract_factory.bar_medicine.BarHeadachePill;
import com.study.til.factory.Customer;
import com.study.til.factory.Medicine;

public class BarMedicineFactory implements AbstractMedicineFactory {

  public Medicine getMedicine(Customer customer) {
    return switch (customer) {
      case COLD -> new BarCold();
      case DIGESTIVE -> new BarDigestive();
      default -> new BarHeadachePill();
    };
  }
}
