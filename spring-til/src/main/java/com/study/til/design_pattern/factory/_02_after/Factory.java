package com.study.til.design_pattern.factory._02_after;

import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;

public interface Factory {
    Medicine getMedicine(Customer customer);
}
