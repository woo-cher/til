package com.study.til.strategy._01_before;

public class Client {
  public static void main(String[] args) {
    SearchButton searchButton = new SearchButton();
    searchButton.onclick();

    // 이미지 모드로 변경
    searchButton.setSearchType(SearchType.IMAGE);
    searchButton.onclick();

    // 뉴스 모드로 변경
    searchButton.setSearchType(SearchType.NEWS);
    searchButton.onclick();

    // 지도 모드로 변경
    searchButton.setSearchType(SearchType.MAP);
    searchButton.onclick();
  }
}

/** 모든 컨텐츠 검색 이미지 컨텐츠 검색 뉴스 컨텐츠 검색 지도 컨텐츠 검색 */
