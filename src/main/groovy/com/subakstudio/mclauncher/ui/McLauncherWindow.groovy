package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.Constants
import com.subakstudio.mclauncher.util.MinecraftUtils
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.PlatformUtils
import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel

import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JOptionPane
import java.awt.BorderLayout
import java.awt.Component
import java.awt.BorderLayout as BL
import java.awt.GridBagConstraints

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
class McLauncherWindow {
    def swing = new SwingBuilder()

    def McLauncherWindow() {
        swing.edt {
            lookAndFeel 'nimbus'
//        lookAndFeel new SubstanceEmeraldDuskLookAndFeel()
//        lookAndFeel new SubstanceBusinessLookAndFeel()
//            lookAndFeel new SubstanceAutumnLookAndFeel()
//            lookAndFeel new SubstanceGraphiteLookAndFeel()
        }

        swing.doLater {
            setupUI()
        }
    }

    def setupUI() {
        swing.frame(id: 'mainFrame', size: [400, 300], defaultCloseOperation: JFrame.EXIT_ON_CLOSE, show: true) {
            borderLayout()
//            progressBar(id: 'progressBar2', constraints: BL.NORTH)
//            gridBagLayout()
//            panel(constraints: BL.CENTER, layout: gridLayout(cols: 2, rows: 5)) {
//                (0..<10).each {
//                    println it
//                    swing.label it.toString()
//                }
//            }
//            panel(constraints: gbc(gridx: 0, gridy: 0, fill: GridBagConstraints.BOTH, gridwidth: GridBagConstraints.RELATIVE)) {
            panel(constraints: BL.CENTER, border: BorderFactory.createEmptyBorder(10, 10, 10, 10)) {
//                gridBagLayout()
//                label(text: "Hello",
//                        constraints: gbc(gridx: 0, gridy: 0, gridwidth: GridBagConstraints.REMAINDER, fill: GridBagConstraints.HORIZONTAL, insets: [10, 10, 10, 10]))
                progressBar(id: 'progressBar', constraints: gbc(gridx: 0, gridy: 0, fill: GridBagConstraints.HORIZONTAL))
//                button(text: "Click me",
//                        constraints: gbc(gridx: 0, gridy: 1, fill: GridBagConstraints.HORIZONTAL))
//                label(text: "Hello",
//                        constraints: gbc(gridx: 0, gridy: 2))
            }
//            panel(constraints: BL.CENTER, layout: gridLayout(cols: 1, rows: 4)) {
//                button(text: 'Execute Minecraft', actionPerformed: {
//                    def output = "java -jar /home/yeoupooh/apps/minecraft/Minecraft.jar".execute().text
//                    log.debug("output=$output")
//                })
//                button(text: 'Download MinecraftForge', actionPerformed: {
//                    downloadMcForge()
//                })
//                button(text: 'Execute MinecraftForge', actionPerformed: {
//                    def output = ['java', '-jar', new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME).absolutePath].execute().text
//                    log.debug("output=$output")
//                })
//                button(text: 'Open File Explorer with Minecraft root', actionPerformed: {
//                    try {
//                        PlatformUtils.openFileBrowser(MinecraftUtils.getMcRoot())
//                    } catch (FileNotFoundException e) {
//                        JOptionPane.showMessageDialog(mainFrame, MinecraftUtils.getMcRoot() + "not found.")
//                    }
//                })
//            }
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
