package com.subakstudio.mclauncher;

/**
 * Created by yeoupooh on 2/19/16.
 */
public class DownloadableModRow {
    private String name;
    private String version;
    private String forgeVersion;
    private boolean viaWeb;
    private String fileName;
    private String url;
    private boolean useWebBrowser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isViaWeb() {
        return viaWeb;
    }

    public void setViaWeb(boolean viaWeb) {
        this.viaWeb = viaWeb;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getForgeVersion() {
        return forgeVersion;
    }

    public void setForgeVersion(String forgeVersion) {
        this.forgeVersion = forgeVersion;
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

    public boolean isUseWebBrowser() {
        return useWebBrowser;
    }

    public void setUseWebBrowser(boolean useWebBrowser) {
        this.useWebBrowser = useWebBrowser;
    }

}

