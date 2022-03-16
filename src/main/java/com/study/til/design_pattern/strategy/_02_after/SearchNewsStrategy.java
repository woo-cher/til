package com.study.til.design_pattern.strategy._02_after;

public class SearchNewsStrategy implements SearchStrategy {

    @Override
    public void search() {
        System.out.println("뉴스 검색 전략");
        // ...
    }
}
