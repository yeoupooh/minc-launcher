package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.Constants
import com.subakstudio.mclauncher.util.MinecraftUtils
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.PlatformUtils
import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

import javax.swing.JOptionPane

/**
 * Created by yeoupooh on 1/1/16.
 */
@Slf4j
class McLauncherController {
    def swing = new SwingBuilder()
    def form

    def McLauncherController() {
        swing.edt {
            lookAndFeel 'nimbus'
//        lookAndFeel new SubstanceEmeraldDuskLookAndFeel()
//        lookAndFeel new SubstanceBusinessLookAndFeel()
//            lookAndFeel new SubstanceAutumnLookAndFeel()
//            lookAndFeel new SubstanceGraphiteLookAndFeel()
        }

        swing.doLater {
            form = new McLauncherForm()
            form.actionListener = { event ->
                log.debug("actionPerformed: $event.actionCommand")
                if (event.actionCommand == Commands.DOWNLOAD_FORGE) {
                    downloadForge()
                } else if (event.actionCommand == Commands.RUN_FORGE_INSTALLER) {
                    def output = ['java', '-jar', new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME).absolutePath].execute().text
                    log.debug("output=$output")
                } else {
                    log.warn("No handled.")
                }
                switch (event.actionCommand) {
                    case Commands.OPEN_INSTALLED_MODS_FOLDER:
                        try {
                            PlatformUtils.openFileBrowser(MinecraftUtils.getMcRoot())
                        } catch (FileNotFoundException e) {
                            JOptionPane.showMessageDialog(form, MinecraftUtils.getMcRoot() + "not found.")
                        }
                        break
                }
            }
        }
    }

    def downloadForge() {
        swing.doOutside {
            def http = new OkHttpClientHelper()
            http.progressListener = { progress ->
                swing.doLater {
                    form.updateProgress(progress)
                }
            }
            http.download(
                    'http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1558-1.7.10/forge-1.7.10-10.13.4.1558-1.7.10-installer.jar',
                    new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME))
        }
    }
}
