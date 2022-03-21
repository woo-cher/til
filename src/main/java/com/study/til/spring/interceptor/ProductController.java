package com.study.til.spring.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ProductController {

    @GetMapping("/products")
    public List<String> getUsers() {
        final List<String> products = new ArrayList<>();
        products.add("hammer");
        products.add("drill");
        products.add("dryer");

        log.info("In Handler (ProductController) method");
        return products;
    }
}
