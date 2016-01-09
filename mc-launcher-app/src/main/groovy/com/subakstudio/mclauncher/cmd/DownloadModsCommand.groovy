package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.MinecraftDataFolder
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
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

            def list = form.getSelectedDownloadableMods()
            list.each {
                def fileName = it.getValueAt(3) as String
                def url = it.getValueAt(4) as String
                log.debug("selected: $fileName: $url")
                http.download(
                        url,
                        new File(MinecraftDataFolder.getModsFolder(new File(settings.mcDataFolder)), fileName))
                swing.doLater {
                    form.updateMessage("Downloaded mods.")
                    form.updateModList(settings.mcDataFolder)
                }
            }
        }

        return true
    }
}
