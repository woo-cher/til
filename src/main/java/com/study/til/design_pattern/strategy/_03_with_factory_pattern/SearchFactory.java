package com.study.til.design_pattern.strategy._03_with_factory_pattern;

import com.study.til.design_pattern.strategy._01_before.SearchType;
import com.study.til.design_pattern.strategy._02_after.*;

public class SearchFactory {

    public SearchStrategy getSearchStrategy(SearchType searchType) {
        return switch (searchType) {
            case ALL -> new SearchAllStrategy();
            case IMAGE -> new SearchImageStrategy();
            case NEWS -> new SearchNewsStrategy();
            default -> new SearchMapStrategy();
        };
    }
}
