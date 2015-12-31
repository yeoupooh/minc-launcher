/**
 * Created by yeoupooh on 12/30/15.
 */
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import groovy.swing.SwingBuilder
import okio.Okio
import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel

import javax.swing.*
import java.awt.BorderLayout as BL

def count = 0

swing = new SwingBuilder()

swing.edt {
//    doLater {
//    lookAndFeel 'nimbus'
//        lookAndFeel new SubstanceEmeraldDuskLookAndFeel()
//        lookAndFeel new SubstanceBusinessLookAndFeel()
    lookAndFeel new SubstanceAutumnLookAndFeel()
//    }
}

def publishProgress(bar, progress) {
    swing.doLater {
        bar.value = progress
    }
}

def request() {
    swing.doOutside {
        println "request..."

        // http://stackoverflow.com/questions/25893030/download-binary-file-from-okhttp
        DOWNLOAD_CHUNK_SIZE = 2048; //Same as Okio Segment.SIZE

        publishProgress(progressBar, 0)

        def url = 'http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1558-1.7.10/forge-1.7.10-10.13.4.1558-1.7.10-installer.jar'

        def client = new OkHttpClient()
        def request = new Request.Builder()
                .url(url)
                .build()
        def response = client.newCall(request).execute()

        def file = new File('forge.jar')
        def sink = Okio.buffer(Okio.sink(file))
        def body = response.body()
        def contentLength = body.contentLength()
        def source = body.source()

        def bytesRead = 0
        while (source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE) != -1) {
            bytesRead += DOWNLOAD_CHUNK_SIZE
            def progress = (int) ((bytesRead * 100) / contentLength)
            println progress
            publishProgress(progressBar, progress)
        }
        sink.writeAll(source)
        sink.close()
        publishProgress(progressBar, 100)

        println "saved"
    }
}

swing.doLater {
    swing.frame(title: 'Frame', size: [300, 300], show: true, defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
        borderLayout()
        textlabel = label(text: "Click the button!", constraints: BL.NORTH)
        progressBar(id: 'progressBar', minimum: 0, maximum: 100, constraints: BL.NORTH)
        button(text: 'Click Me',
                actionPerformed: {
                    count++
                    textlabel.text = "Clicked ${count} time(s)."
                    println "clicked"

                    request()

                },
                constraints: BL.SOUTH
        )
    }
}
