package com.study.til.design_pattern.strategy._02_after;

public class SearchButton {
    private SearchStrategy searchStrategy;

    SearchButton() {
        this.searchStrategy = new SearchAllStrategy();
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void onclick() {
        searchStrategy.search();
    }
}
