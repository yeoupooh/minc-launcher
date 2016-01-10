package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.model.DownloadableForgeRow
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.McProps
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import groovy.json.JsonSlurper

/**
 * Created by Thomas on 1/10/2016.
 */
class RefreshDownloadableForgesCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        def http = new OkHttpClientHelper()
        def json = new JsonSlurper().parseText(http.downloadText(McProps.get("url.json.forges")))
        def list = new ArrayList<DownloadableForgeRow>()
        json.forges.each { forge ->
            def item = new DownloadableForgeRow()
            item.version = forge.version
            item.fileName = FileUtils.getFileNameFromUrl(forge.fileName as String, forge.url as String)
            item.url = forge.url
            list.add(item)
        }
        swing.doLater {
            form.setDownloadableForges(list)
        }

        return true
    }
}
