package com.subakstudio.mclauncher.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.subakstudio.mclauncher.config.DownloadableUrl;
import com.subakstudio.mclauncher.config.WebBrowser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeoupooh on 2/15/16.
 */
public class McLauncherConfig {
    private String version;
    private String modsUrl;
    private String forgesUrl;
    private WebBrowser webBrowser;
    @JsonProperty("downloadables")
    private List<DownloadableUrl> downloadableUrls = new ArrayList<>();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getForgesUrl() {
        return forgesUrl;
    }

    public void setForgesUrl(String forgesUrl) {
        this.forgesUrl = forgesUrl;
    }

    public WebBrowser getWebBrowser() {
        return webBrowser;
    }

    public void setWebBrowser(WebBrowser webBrowser) {
        this.webBrowser = webBrowser;
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
