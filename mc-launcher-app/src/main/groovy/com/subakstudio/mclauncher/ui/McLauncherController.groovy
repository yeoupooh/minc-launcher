package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.Commands
import com.subakstudio.mclauncher.cmd.DownloadForgeCommand
import com.subakstudio.mclauncher.cmd.DownloadModsCommand
import com.subakstudio.mclauncher.cmd.LaunchMinecraftCommand
import com.subakstudio.mclauncher.cmd.RefreshModListCommand
import com.subakstudio.mclauncher.cmd.RunForgeInstallerCommand
import com.subakstudio.mclauncher.cmd.SwingFormCommandDispatcher
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.MinecraftUtils
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
    def settings

    def McLauncherController(settings) {
        this.settings = settings

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

            def cmdDispatcher = new SwingFormCommandDispatcher(swing, form)
            cmdDispatcher.putCommand(Commands.DOWNLOAD_FORGE, new DownloadForgeCommand())
            cmdDispatcher.putCommand(Commands.LAUNCH_MINECRAFT, new LaunchMinecraftCommand())
            cmdDispatcher.putCommand(Commands.DOWNLOAD_MODS_PACK, new DownloadModsCommand())
            cmdDispatcher.putCommand(Commands.REFRESH_MOD_LIST, new RefreshModListCommand())
            cmdDispatcher.putCommand(Commands.RUN_FORGE_INSTALLER, new RunForgeInstallerCommand())
            cmdDispatcher.putCommand(Commands.OPEN_INSTALLED_MODS_FOLDER, new OpenInstalledModsFolderCommand())

            form.actionListener = { event ->

                log.debug("actionPerformed: $event.actionCommand")

                switch (event.actionCommand) {

                    case Commands.OPEN_UNINSTALLED_MODS_FOLDER:
                        try {
                            PlatformUtils.openFileBrowser(MinecraftUtils.downloadedModsDir)
                        } catch (FileNotFoundException e) {
                            JOptionPane.showMessageDialog(form, MinecraftUtils.downloadedModsDir + "not found.")
                        }
                        break

                    case Commands.INSTALL_SELECTED_MODS:
                        installSelected()
                        break

                    case Commands.UNINSTALL_SELECTED_MODS:
                        uninstallSelected()
                        break

                    default:
                        if (!cmdDispatcher.execute(event.actionCommand)) {
                            log.warn("Unhandled action: $event.actionCommand")
                        }
                }
            }

            form.tableModelListener = { event ->
                log.debug("tableChanged: firstRow=[$event.firstRow] lastRow=[$event.lastRow] column=[$event.column] type=[$event.type] src=[$event.source] event=[$event]")

            }

            initLists()

        } // doLater
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
        form.updateModList(MinecraftUtils.mcDataFolder);
    }

    def initFolders() {
        FileUtils.mkdirs(MinecraftUtils.modsDir)
        FileUtils.mkdirs(MinecraftUtils.downloadedModsDir)
    }

}
