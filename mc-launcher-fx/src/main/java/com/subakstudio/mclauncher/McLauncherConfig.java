package com.subakstudio.mclauncher;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeoupooh on 2/15/16.
 */
public class McLauncherConfig {
    public String getForgesUrl() {
        return forgesUrl;
    }

    public void setForgesUrl(String forgesUrl) {
        this.forgesUrl = forgesUrl;
    }

    public List<DownloadableUrl> getDownloadableUrls() {
        return downloadableUrls;
    }

    public void setDownloadableUrls(List<DownloadableUrl> downloadableUrls) {
        this.downloadableUrls = downloadableUrls;
    }

    private String forgesUrl;
    @JsonProperty("downloadables")
    private List<DownloadableUrl> downloadableUrls = new ArrayList<>();
}
