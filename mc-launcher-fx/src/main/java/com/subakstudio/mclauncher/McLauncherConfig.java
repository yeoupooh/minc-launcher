package com.subakstudio.mclauncher;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeoupooh on 2/15/16.
 */
public class McLauncherConfig {
    private String modsUrl;
    private String forgesUrl;
    @JsonProperty("downloadables")
    private List<DownloadableUrl> downloadableUrls = new ArrayList<>();

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

    public String getModsUrl() {
        return modsUrl;
    }

    public void setModsUrl(String modsUrl) {
        this.modsUrl = modsUrl;
    }
}
