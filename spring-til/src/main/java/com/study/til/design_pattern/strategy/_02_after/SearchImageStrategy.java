package com.study.til.design_pattern.strategy._02_after;

public class SearchImageStrategy implements SearchStrategy {

    @Override
    public void search() {
        System.out.println("이미지 검색 전략");
        // ...
    }
}
