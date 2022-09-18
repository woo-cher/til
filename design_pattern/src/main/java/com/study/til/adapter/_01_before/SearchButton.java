package com.study.til.adapter._01_before;

import com.study.til.adapter.FindStrategy;
import com.study.til.strategy._02_after.SearchAllStrategy;
import com.study.til.strategy._02_after.SearchStrategy;

/**
 *  만약에 새로 추가된 `동영상 검색` 로직을 추가해야 한다면??
 */
public class SearchButton {
    private SearchStrategy searchStrategy;
    private FindStrategy findStrategy;

    public SearchButton() {
        this.searchStrategy = new SearchAllStrategy();
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void setFindStrategy(FindStrategy findStrategy) {
        this.findStrategy = findStrategy;
    }

    public void onclick(Class clazz) {
        if (clazz.equals(SearchStrategy.class)) {
            searchStrategy.search();
        } else {
            findStrategy.find(true);
        }
    }
}
