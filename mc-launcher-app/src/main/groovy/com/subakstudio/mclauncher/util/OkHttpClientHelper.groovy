package com.subakstudio.mclauncher.util

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import groovy.util.logging.Slf4j
import okio.Okio

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
class OkHttpClientHelper {

    def progressListener

    def download(String url, File file) {
        log.info("request...$url to $file")

        // http://stackoverflow.com/questions/25893030/download-binary-file-from-okhttp
        def DOWNLOAD_CHUNK_SIZE = 2048 //Same as Okio Segment.SIZE

        publishProgress(0)

        def client = new OkHttpClient()
        def request = new Request.Builder()
                .url(url)
                .build()
        def response = client.newCall(request).execute()

//        def file = new File('forge.jar')
        def sink = Okio.buffer(Okio.sink(file))
        def body = response.body()
        def contentLength = body.contentLength()
        def source = body.source()

        def bytesRead = 0L
        def totalBytesRead = 0

        while (true) {
            bytesRead = source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE)
            if (-1L == bytesRead) {
                break
            }
            totalBytesRead += bytesRead
            def progress = (int) ((totalBytesRead * 100) / contentLength)
//            log.debug("progress=$progress")
            publishProgress(progress)
        }
        sink.writeAll(source)
        sink.close()
        publishProgress(100)

        log.info("saved.")
    }

    def downloadText(url) {
        def client = new OkHttpClient()
        def request = new Request.Builder()
                .url(url)
                .build()
        def response = client.newCall(request).execute()
        return response.body().string()
    }

    def publishProgress(progress) {
        if (progressListener != null) {
            progressListener.call(progress)
        }
    }
}
