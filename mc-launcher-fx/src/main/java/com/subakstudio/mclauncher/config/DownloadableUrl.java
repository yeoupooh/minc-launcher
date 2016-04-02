package com.subakstudio.mclauncher.config;

/**
 * Created by yeoupooh on 2/15/16.
 */
public class DownloadableUrl {
    private String startsWith;
    private String endsWith;
    private String match;

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public String getEndsWith() {
        return endsWith;
    }

    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
    }
}
