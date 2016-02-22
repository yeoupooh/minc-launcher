package com.subakstudio.mclauncher;

/**
 * Created by yeoupooh on 2/22/16.
 */
public class ForgeRow {
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String version;
    private String fileName;
    private String url;
}
