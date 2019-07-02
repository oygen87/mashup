package com.example.cygnimashup.model.wikipedia;

import java.util.Map;

public class WikipediaQuery {
    private Map<Integer, Page> pages;

    public Map<Integer, Page> getPages() {
        return pages;
    }

    public void setPages(Map<Integer, Page> pages) {
        this.pages = pages;
    }
}
