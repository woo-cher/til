package com.study.til.design_pattern.adapter._02_after;

import com.study.til.design_pattern.adapter.FindVideoStrategy;
import com.study.til.design_pattern.strategy._02_after.SearchButton;
import com.study.til.design_pattern.strategy._02_after.SearchImageStrategy;

public class Client {
    public static void main(String[] args) {
        // 모든 컨텐츠 검색
        SearchButton searchButton = new SearchButton();
        searchButton.onclick();

        // 이미지 검색
        searchButton.setSearchStrategy(new SearchImageStrategy());
        searchButton.onclick();

        // ...

        // 타사에서 만든 동영상 검색
        searchButton.setSearchStrategy(new SearchFindAdapter(new FindVideoStrategy()));
        searchButton.onclick();
    }
}
