package com.study.til.adapter._01_before;

import com.study.til.adapter.FindVideoStrategy;
import com.study.til.strategy._02_after.SearchImageStrategy;
import com.study.til.strategy._02_after.SearchStrategy;

/**
 *  using example with `strategy pattern` example
 *
 *  @see com.study.til.strategy
 */
public class Client {
    public static void main(String[] args) {

        // 우리 회사에서 만든 검색 전략
        SearchButton searchButton = new SearchButton();
        searchButton.onclick(SearchStrategy.class);

        searchButton.setSearchStrategy(new SearchImageStrategy());
        searchButton.onclick(SearchStrategy.class);

        // 타 회사에서 만든 동영상 검색 전략
        searchButton.setFindStrategy(new FindVideoStrategy());
        searchButton.onclick(FindVideoStrategy.class);
    }
}
