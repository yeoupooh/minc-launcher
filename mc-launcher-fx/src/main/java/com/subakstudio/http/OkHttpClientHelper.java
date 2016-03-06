package com.subakstudio.http;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;

/**
 * Created by Thomas on 2/4/2016.
 */
@Slf4j
public class OkHttpClientHelper {

    private final CookieManager cookieManager;

    public OkHttpClientHelper(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    public void downloadBinary(String url, File file) throws IOException {
        log.info(String.format("request...%s to %s", url, file));

        // http://stackoverflow.com/questions/25893030/download-binary-file-from-okhttp
        int DOWNLOAD_CHUNK_SIZE = 2048; //Same as Okio Segment.SIZE

        publishProgress(0);

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();

        BufferedSink sink = Okio.buffer(Okio.sink(file));
        ResponseBody body = response.body();
        long contentLength = body.contentLength();
        BufferedSource source = body.source();

        long bytesRead = 0L;
        long totalBytesRead = 0;

        while (true) {
            bytesRead = source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE);
            if (-1L == bytesRead) {
                break;
            }
            totalBytesRead += bytesRead;
            int progress = (int) ((totalBytesRead * 100) / contentLength);
            publishProgress(progress);
        }
        sink.writeAll(source);
        sink.close();
        publishProgress(100);

        log.info("saved.");
    }

    public String downloadText(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void publishProgress(int progress) {

    }

}
