package com.study.til.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterInjection {

  private SomeRepository someRepository;

  @Autowired
  public void setSomeRepository(SomeRepository someRepository) { // Setter Injection
    this.someRepository = someRepository;
  }

  public void save(String foo) {
    someRepository.insert(foo);
  }
}
