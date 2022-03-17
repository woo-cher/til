package com.study.til.design_pattern.strategy._03_with_factory_pattern;

import com.study.til.design_pattern.strategy._01_before.SearchType;
import com.study.til.design_pattern.strategy._02_after.SearchStrategy;

/**
 * If use strategy pattern with factory method pattern.
 *
 * <br>
 * factory pattern is ...
 *
 * @see com.study.til.design_pattern.factory
 */
public class Client {

    public static void main(String[] args) {
        SearchFactory factory = new SearchFactory();

        SearchStrategy searchStrategy = factory.getSearchStrategy(SearchType.ALL);
        searchStrategy.search();

        searchStrategy = factory.getSearchStrategy(SearchType.IMAGE);
        searchStrategy.search();

        searchStrategy = factory.getSearchStrategy(SearchType.NEWS);
        searchStrategy.search();

        searchStrategy = factory.getSearchStrategy(SearchType.MAP);
        searchStrategy.search();
    }
}
