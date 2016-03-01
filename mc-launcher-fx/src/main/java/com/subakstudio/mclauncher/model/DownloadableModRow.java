package com.subakstudio.mclauncher.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yeoupooh on 2/19/16.
 */
@Slf4j
public class DownloadableModRow {
    private String name;
    private String version;
    private String forgeVersion;
    private SimpleBooleanProperty viaWeb = new SimpleBooleanProperty();
    private String fileName;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isViaWeb() {
        return viaWeb.get();
    }

    public void setViaWeb(boolean viaWeb) {
        this.viaWeb.set(viaWeb);
    }

    public SimpleBooleanProperty viaWebProperty() {
        return viaWeb;
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

    @Override
    public String toString() {
        return String.format("%s{name=[%s],viaWeb=[%s],url=[%s]}", getClass().getSimpleName(), name, viaWeb, url);
    }
}

