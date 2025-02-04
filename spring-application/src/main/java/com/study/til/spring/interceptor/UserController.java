package com.study.til.spring.interceptor;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

  @GetMapping("/users")
  public List<String> getUsers() {
    final List<String> users = new ArrayList<>();
    users.add("Foo");
    users.add("Bar");
    users.add("Baz");

    log.info("In Handler (UserController) method");
    return users;
  }
}
