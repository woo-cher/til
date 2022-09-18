package com.study.til.design_pattern.strategy._01_before;

public class SearchButton {
    private SearchType searchType;

    public SearchButton() {
        this.searchType = SearchType.ALL;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public void onclick() {
        if (this.searchType.equals(SearchType.ALL)) {
            System.out.println("모든 컨텐츠 검색");

            // do search `all`

        } else if (this.searchType.equals(SearchType.IMAGE)) {
            System.out.println("이미지 컨텐츠 검색");

            // do search `image`

        } else if (this.searchType.equals(SearchType.NEWS)) {
            System.out.println("뉴스 컨텐츠 검색");

            // do search `news`

        } else if (this.searchType.equals(SearchType.MAP)) {
            System.out.println("지도 컨텐츠 검색");

            // do search `map`
        }
    }
}
