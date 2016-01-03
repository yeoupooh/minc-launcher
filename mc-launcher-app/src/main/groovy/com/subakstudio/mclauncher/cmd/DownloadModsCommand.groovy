package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.MinecraftDataFolder
import com.subakstudio.mclauncher.util.MinecraftUtils
import com.subakstudio.mclauncher.util.OkHttpClientHelper

/**
 * Created by Thomas on 1/3/2016.
 */
class DownloadModsCommand extends SwingFormCommand {

    @Override
    boolean execute() {
        swing.doOutside {
            swing.doLater {
                form.updateMessage("Downloading mods...")
            }
            def http = new OkHttpClientHelper()
            http.progressListener = { progress ->
                swing.doLater {
                    form.updateMessage("Downloading mods...$progress %")
                    form.updateProgress(progress)
                }
            }
            http.download(
                    'http://download.nodecdn.net/containers/pixelmon/core/Pixelmon-1.7.10-3.5.1-universal.jar',
                    new File(MinecraftDataFolder.getModsFolder(new File(MinecraftUtils.mcDataFolder)), 'Pixelmon-1.7.10-3.5.1-universal.jar'))
            swing.doLater {
                form.updateMessage("Downloaded mods.")
                form.updateModList(MinecraftUtils.mcDataFolder)
//                form.modsPanel.updateInstalledModList(MinecraftUtils.downloadedModsDir)
            }
        }

        return true
    }
}
