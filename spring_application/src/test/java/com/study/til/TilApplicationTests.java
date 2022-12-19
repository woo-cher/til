package com.study.til;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TilApplicationTests {

  @Autowired private DataSource dataSource;

  @Test
  void dbConnect() {
    log.info("dataSource : {}", dataSource);
    Assertions.assertNotNull(dataSource);
  }
}
