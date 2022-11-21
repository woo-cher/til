package com.study.til.factory._02_after;

import com.study.til.factory.Customer;
import com.study.til.factory.Medicine;

public interface Factory {
  Medicine getMedicine(Customer customer);
}
