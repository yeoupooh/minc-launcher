package com.subakstudio.mclauncher;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeoupooh on 2/15/16.
 */
public class McLauncherConfig {
    public List<DownloadableUrl> getDownloadableUrls() {
        return downloadableUrls;
    }

    public void setDownloadableUrls(List<DownloadableUrl> downloadableUrls) {
        this.downloadableUrls = downloadableUrls;
    }

    @JsonProperty("downloadables")
    private List<DownloadableUrl> downloadableUrls = new ArrayList<>();
}
