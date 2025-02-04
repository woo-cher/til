package com.study.til.abstract_factory;

import com.study.til.factory.Customer;
import com.study.til.factory.Medicine;

public interface AbstractMedicineFactory {
  Medicine getMedicine(Customer customer);
}
