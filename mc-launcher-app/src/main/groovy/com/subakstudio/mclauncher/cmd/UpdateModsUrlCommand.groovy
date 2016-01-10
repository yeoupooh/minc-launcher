package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.model.DownloadableModRow
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.McProps
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import groovy.json.JsonSlurper

/**
 * Created by Thomas on 1/10/2016.
 */
class UpdateModsUrlCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        settings.modsUrl = form.getModsUrl()
        settings.save()
        return true
    }
}
