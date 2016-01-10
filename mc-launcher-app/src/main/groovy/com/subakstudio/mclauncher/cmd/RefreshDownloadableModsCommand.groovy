package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.model.DownloadableModRow
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import groovy.json.JsonSlurper

/**
 * Created by Thomas on 1/10/2016.
 */
class RefreshDownloadableModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        swing.doOutside {
            def http = new OkHttpClientHelper()
            def json = new JsonSlurper().parseText(http.downloadText(settings.modsUrl))
            def list = new ArrayList<DownloadableModRow>()
            json.mods.each { mod ->
                def item = new DownloadableModRow()
                item.name = mod.name
                item.requiredVersion = mod.requiredVersion
                item.url = mod.url
                item.version = mod.version
                item.fileName = FileUtils.getFileNameFromUrl(mod.fileName as String, mod.url as String)
                list.add(item)
            }
            swing.doLater {
                form.setDownloadableMods(list)
            }
        }

        return true
    }
}
