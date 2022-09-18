package com.study.til.strategy._02_after;

public class SearchButton {
    private SearchStrategy searchStrategy;

    public SearchButton() {
        this.searchStrategy = new SearchAllStrategy();
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void onclick() {
        searchStrategy.search();
    }
}
