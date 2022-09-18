package com.study.til.design_pattern.strategy._02_after;

public class Client {
    public static void main(String[] args) {
        SearchButton searchButton = new SearchButton();
        searchButton.onclick();

        // 이미지 모드로 변경
        searchButton.setSearchStrategy(new SearchImageStrategy());
        searchButton.onclick();

        // 뉴스 모드로 변경
        searchButton.setSearchStrategy(new SearchNewsStrategy());
        searchButton.onclick();

        // 지도 모드로 변경
        searchButton.setSearchStrategy(new SearchMapStrategy());
        searchButton.onclick();
    }
}
