package com.subakstudio.mclauncher.config;

import java.util.List;

/**
 * Created by yeoupooh on 3/1/16.
 */
public class WebBrowser {

    private String initialUrl;
    private List<Bookmark> bookmarks;

    public String getInitialUrl() {
        return initialUrl;
    }

    public void setInitialUrl(String initialUrl) {
        this.initialUrl = initialUrl;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

}
