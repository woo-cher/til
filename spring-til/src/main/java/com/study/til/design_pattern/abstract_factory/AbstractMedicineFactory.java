package com.study.til.design_pattern.abstract_factory;

import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;

public interface AbstractMedicineFactory {
    Medicine getMedicine(Customer customer);
}
