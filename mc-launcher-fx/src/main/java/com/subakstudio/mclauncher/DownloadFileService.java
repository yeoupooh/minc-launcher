package com.subakstudio.mclauncher;

import com.subakstudio.http.OkHttpClientHelper;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.net.CookieManager;

/**
 * Created by yeoupooh on 2/19/16.
 */
public class DownloadFileService extends Service {

    private final IDownloadEventHandler eventHandler;
    private String url;
    private String fileName;
    private CookieManager cookieManager;

    public DownloadFileService(IDownloadEventHandler callback) {
        this.eventHandler = callback;
    }

    public void setUrl(String value) {
        url = value;
    }

    public final String getUrl() {
        return url;
    }

    public void setCookieManager(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    @Override
    protected Task createTask() {
        final String _url = getUrl();
        final String _fileName = getFileName();
        final CookieManager _cookieManager = getCookieManager();

        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                OkHttpClientHelper httpClient = new OkHttpClientHelper(_cookieManager);
                httpClient.downloadBinary(_url, new File(_fileName));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        eventHandler.completed();
                    }
                });
                return true;
            }
        };
    }

    private CookieManager getCookieManager() {
        return cookieManager;

    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
