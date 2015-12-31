package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.Constants
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.TempRepository
import groovy.swing.SwingBuilder

import javax.swing.JFrame

/**
 * Created by yeoupooh on 12/30/15.
 */
class McLauncherWindow {
    def swing = new SwingBuilder()

    def McLauncherWindow() {
        swing.edt {
            lookAndFeel 'nimbus'
//        lookAndFeel new SubstanceEmeraldDuskLookAndFeel()
//        lookAndFeel new SubstanceBusinessLookAndFeel()
//            lookAndFeel new SubstanceAutumnLookAndFeel()
        }

        swing.doLater {
            TempRepository.init()

            setupUI()
        }
    }

    def setupUI() {
        swing.frame(size: [400, 300], defaultCloseOperation: JFrame.EXIT_ON_CLOSE, show: true) {
            vbox {
                progressBar(id: 'progressBar')
                button(text: 'Execute Minecraft', actionPerformed: {
                    def output = "java -jar /home/yeoupooh/apps/minecraft/Minecraft.jar -Dmy.property=foo".execute().text
                    log.debug("output=$output")
                })
                button(text: 'Download MinecraftForge', actionPerformed: {
                    downloadMcForge()
                })
                button(text: 'Execute MinecraftForge', actionPerformed: {
                    def output = ['java', '-jar', new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME).absolutePath].execute().text
                    log.debug("output=$output")
                })
                button(text: 'Open File Explorer on Windows', actionPerformed: {
                    def output = ['explorer', 'c:\\windows'].execute()
//                    Runtime.getRuntime().exec("explorer.exe /select," + path);

                    // http://stackoverflow.com/questions/7357969/how-to-use-java-code-to-open-windows-file-explorer-and-highlight-the-specified-f
//                    File file = new File ("c:\<directory>");
//                    Desktop desktop = Desktop.getDesktop();
//                    desktop.open(file);

                    log.debug("output=$output")
                })
                button(text: 'Open Files on Ubuntu', actionPerformed: {
                    def path = System.getProperty('user.home') + '/.minecraft'
                    def output = ['/usr/bin/xdg-open', path].execute().text
//                    Runtime.getRuntime().exec("xdg-open " + path);
                    log.debug("output=$output")
                })
            }
        }
    }

    def downloadMcForge() {
        swing.doOutside {
            def http = new OkHttpClientHelper()
            http.progressListener = { progress ->
                swing.doLater {
                    progressBar.value = progress
                }
            }
            http.download(
                    'http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1558-1.7.10/forge-1.7.10-10.13.4.1558-1.7.10-installer.jar',
                    new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME))
        }
    }
}
