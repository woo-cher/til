package com.study.til.di;

import com.study.til.spring_core.domain.TilModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SomeRepository {
    public void insert(String foo) {
        log.info("insert Foo : {}", foo);
    }

    public void saveSome(TilModel model) {
        log.info("save object {}", model);
    }
}
