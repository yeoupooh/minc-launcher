package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.MinecraftDataFolder
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.ResStrings
import groovy.util.logging.Slf4j

import javax.swing.JComponent
import javax.swing.JOptionPane
import java.awt.Component

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class DownloadModsCommand extends SwingFormCommand {

    @Override
    boolean execute() {
        swing.doOutside {
            swing.doLater {
                form.updateMessage(ResStrings.get("msg.downloading.mod"))
            }
            def http = new OkHttpClientHelper()
            http.progressListener = { progress ->
                swing.doLater {
                    form.updateMessage(String.format("%s...%s%%", ResStrings.get("msg.downloading.mod"), progress))
                    form.updateProgress(progress)
                }
            }

            def list = form.getSelectedDownloadableMods()

            if (list.size > 0) {
                list.each {
                    def fileName = it.getValueAt(3) as String
                    def url = it.getValueAt(4) as String
                    log.debug("selected: $fileName: $url")

                    // Skip if already downloaded
                    def fileInEnabled = new File(MinecraftDataFolder.getModsFolder(new File(settings.mcDataFolder)), fileName)
                    def fileInDisabled = new File(MinecraftDataFolder.getDisabledModsFolder(new File(settings.mcDataFolder)), fileName)
                    if (!fileInEnabled.exists() && !fileInDisabled.exists()) {
                        http.download(
                                url,
                                new File(MinecraftDataFolder.getModsFolder(new File(settings.mcDataFolder)), fileName))
                        swing.doLater {
                            form.updateModList(settings.mcDataFolder)
                        }
                    } else {
                        log.info("already downloaded.")
                    }
                }
                swing.doLater {
                    form.updateMessage(ResStrings.get("msg.downloaded.mod"))
                }
            } else {
                swing.doLater {
                    JOptionPane.showMessageDialog(form as Component, ResStrings.get("msg.select.mod.to.download"))
                }
            }
        }

        return true
    }
}
