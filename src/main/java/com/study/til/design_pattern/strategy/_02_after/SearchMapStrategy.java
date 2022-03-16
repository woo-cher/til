package com.study.til.design_pattern.strategy._02_after;

public class SearchMapStrategy implements SearchStrategy {

    @Override
    public void search() {
        System.out.println("지도 검색 전략");
        // ...
    }
}
