package com.study.til.strategy._03_with_factory_pattern;

import com.study.til.strategy._01_before.SearchType;
import com.study.til.strategy._02_after.SearchAllStrategy;
import com.study.til.strategy._02_after.SearchImageStrategy;
import com.study.til.strategy._02_after.SearchMapStrategy;
import com.study.til.strategy._02_after.SearchNewsStrategy;
import com.study.til.strategy._02_after.SearchStrategy;

public class SearchFactory {

  public SearchStrategy getSearchStrategy(SearchType searchType) {
    switch (searchType) {
      case ALL:
        return new SearchAllStrategy();
      case IMAGE:
        return new SearchImageStrategy();
      case NEWS:
        return new SearchNewsStrategy();
      default:
        return new SearchMapStrategy();
    }
  }
}
