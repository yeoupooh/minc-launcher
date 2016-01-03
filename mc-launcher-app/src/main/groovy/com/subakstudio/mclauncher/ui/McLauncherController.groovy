package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.Constants
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.MinecraftUtils
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.PlatformUtils
import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel

import javax.swing.JOptionPane

/**
 * Created by yeoupooh on 1/1/16.
 */
@Slf4j
class McLauncherController {
    def swing = new SwingBuilder()
    def form

    def McLauncherController() {
        swing.doLater {
            swing.edt {
                lookAndFeel 'nimbus'
//        lookAndFeel new SubstanceEmeraldDuskLookAndFeel()
//        lookAndFeel new SubstanceBusinessLookAndFeel()
//            lookAndFeel new SubstanceAutumnLookAndFeel()
//                lookAndFeel new SubstanceGraphiteLookAndFeel()
            }
        }

        initFolders()

        swing.doLater {
//            form = new McLauncherForm()
            form = new McLauncherSimple()
            form.actionListener = { event ->

                log.debug("actionPerformed: $event.actionCommand")

                switch (event.actionCommand) {

                    case Commands.DOWNLOAD_FORGE:
                        downloadForge()
                        break

                    case Commands.RUN_FORGE_INSTALLER:
                        def output = ['java', '-jar', new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME).absolutePath].execute().text
                        log.debug("output=$output")
                        break

                    case Commands.DOWNLOAD_MODS_PACK:
                        downloadMods()
                        break

                    case Commands.OPEN_INSTALLED_MODS_FOLDER:
                        try {
                            PlatformUtils.openFileBrowser(MinecraftUtils.modsDir)
                        } catch (FileNotFoundException e) {
                            JOptionPane.showMessageDialog(form, MinecraftUtils.modsDir + "not found.")
                        }
                        break

                    case Commands.OPEN_UNINSTALLED_MODS_FOLDER:
                        try {
                            PlatformUtils.openFileBrowser(MinecraftUtils.downloadedModsDir)
                        } catch (FileNotFoundException e) {
                            JOptionPane.showMessageDialog(form, MinecraftUtils.downloadedModsDir + "not found.")
                        }
                        break

                    case Commands.INSTALL_MODS_PACK:
                        break

                    case Commands.INSTALL_SELECTED_MODS:
                        installSelected()
                        break

                    case Commands.UNINSTALL_SELECTED_MODS:
                        uninstallSelected()
                        break

                    case Commands.LAUNCH_MINECRAFT:
                        launchMinecraft()
                        break

                    default:
                        log.warn("Unhandled action: $event.actionCommand")
                }
            }

            initLists()

        } // doLater
    }

    def launchMinecraft() {
        form.selectedMods.each { mod ->
            log.debug("file=[$mod.file]")
        }

        if (form.mcExecutable.length() == 0) {
            JOptionPane.showMessageDialog(form, "Minecraft Executable is not set.")
        }
    }

    def uninstallSelected() {
        def selected = form.modsPanel.selectedInstalledMods
        if (selected != null) {
            selected.each { mod ->
                FileUtils.delete(new File(MinecraftUtils.modsDir, mod))
            }
            form.modsPanel.reloadModLists()
        } else {
            log.debug("no selection")
        }
    }

    def installSelected() {
        def selected = form.modsPanel.selectedDownloadedMods
        if (selected != null) {
            selected.each { mod ->
                log.debug("mod=[$mod] toDir=[$MinecraftUtils.modsDir]")
                FileUtils.copy(
                        new File(MinecraftUtils.downloadedModsDir, mod),
                        new File(MinecraftUtils.modsDir, mod)
                )
            }
            form.modsPanel.reloadModLists()
        } else {
            log.debug("no selection")
        }
    }

    def initLists() {
//        form.modsPanel.updateInstalledModList(MinecraftUtils.modsDir)
//        form.modsPanel.updateDownloadedModList(MinecraftUtils.downloadedModsDir)
        form.updateModList(MinecraftUtils.mcRoot);
    }

    def initFolders() {
        FileUtils.mkdirs(MinecraftUtils.modsDir)
        FileUtils.mkdirs(MinecraftUtils.downloadedModsDir)
    }

    def downloadForge() {
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
    }

    def downloadMods() {
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
                    new File(MinecraftUtils.downloadedModsDir, 'Pixelmon-1.7.10-3.5.1-universal.jar'))
            swing.doLater {
                form.updateMessage("Downloaded mods.")
                form.modsPanel.updateInstalledModList(MinecraftUtils.downloadedModsDir)
            }
        }
    }
}
