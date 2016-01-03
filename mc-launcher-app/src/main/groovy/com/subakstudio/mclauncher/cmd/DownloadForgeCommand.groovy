package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.Constants
import com.subakstudio.mclauncher.util.OkHttpClientHelper

/**
 * Created by Thomas on 1/3/2016.
 */
class DownloadForgeCommand extends SwingFormCommand {

    @Override
    def boolean execute() {
        swing.doOutside {
            swing.doLater {
                form.updateMessage("Downloading forge...")
            }
            def http = new OkHttpClientHelper()
            http.progressListener = { progress ->
                swing.doLater {
                    form.updateMessage("Downloading forge...$progress %")
                    form.updateProgress(progress)
                }
            }
            http.download(
                    'http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1558-1.7.10/forge-1.7.10-10.13.4.1558-1.7.10-installer.jar',
                    new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME))
            swing.doLater {
                form.updateMessage("Downloaded forge.")
            }
        }

        return true
    }
}
