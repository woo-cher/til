package com.study.til.factory._02_after;

import com.study.til.factory.Cold;
import com.study.til.factory.Customer;
import com.study.til.factory.Digestive;
import com.study.til.factory.HeadachePill;
import com.study.til.factory.Medicine;

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
