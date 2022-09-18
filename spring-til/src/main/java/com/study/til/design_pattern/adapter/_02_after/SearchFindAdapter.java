package com.study.til.design_pattern.adapter._02_after;

import com.study.til.design_pattern.adapter.FindStrategy;
import com.study.til.design_pattern.strategy._02_after.SearchStrategy;

public class SearchFindAdapter implements SearchStrategy {
    private FindStrategy findStrategy;

    public SearchFindAdapter(FindStrategy findStrategy) {
        this.findStrategy = findStrategy;
    }

    @Override
    public void search() {
        findStrategy.find(true);
    }
}
