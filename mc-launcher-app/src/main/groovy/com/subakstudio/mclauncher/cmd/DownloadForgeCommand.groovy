package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.Constants
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.ResStrings
import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class DownloadForgeCommand extends SwingFormCommand {

    @Override
    def boolean execute() {
        swing.doOutside {
            swing.doLater {
                form.updateMessage(ResStrings.get("msg.downloading.forge"))
            }
            def http = new OkHttpClientHelper()
            http.progressListener = { progress ->
                swing.doLater {
                    form.updateMessage(String.format("%s...%s%%", ResStrings.get("msg.downloading.forge"), progress))
                    form.updateProgress(progress)
                }
            }

            def list = form.getSelectedDownloadableForges()
            if (list.size > 0) {
                list.each {
                    def fileName = it.getValueAt(1) as String
                    def url = it.getValueAt(2) as String
                    log.debug("selected: $fileName: $url")
                    http.download(
                            url,
                            new File(Constants.MC_LAUNCHER_REPO_FORGES_FOLDER, fileName))
                    swing.doLater {
                        form.updateMessage(ResStrings.get("msg.downloaded.forge"))
                    }
                }
            } else {
                dialogBuilder.buildErrorWithResId("msg.select.forge.to.download").show()
            }
        }

        return true
    }
}
