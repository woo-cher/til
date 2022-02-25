package com.study.til.di;

import com.study.til.spring_core.repository.SomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstructorInjection {

    private SomeRepository repository;

    /**
     *  생성자 DI 가 하나라면, @Autowired 생략이 가능하다
     */
    @Autowired
    public ConstructorInjection(SomeRepository repository) { // Constructor Injection
        this.repository = repository;
    }

    public void save(String foo) {
        repository.insert(foo);
    }
}
