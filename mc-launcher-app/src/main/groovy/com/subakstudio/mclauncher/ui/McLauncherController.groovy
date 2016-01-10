package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.Commands
import com.subakstudio.mclauncher.MinecraftDataFolder
import com.subakstudio.mclauncher.cmd.*
import com.subakstudio.mclauncher.model.DownloadableForgeRow
import com.subakstudio.mclauncher.model.DownloadableModRow
import com.subakstudio.mclauncher.model.Settings
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.McProps
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.ResStrings
import groovy.json.JsonSlurper
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
    def cmdDispatcher
    Settings settings

    def McLauncherController(Settings settings) {
        this.settings = settings

        swing.doLater {
            swing.edt {
                lookAndFeel 'nimbus'
                // FIXME Substance has issue with checkbox rendering
//        lookAndFeel new SubstanceEmeraldDuskLookAndFeel()
//        lookAndFeel new SubstanceBusinessLookAndFeel()
//            lookAndFeel new SubstanceAutumnLookAndFeel()
//                lookAndFeel new SubstanceGraphiteLookAndFeel()
            }
        }

        initFolders()

        swing.doLater {

            form = new McLauncherSimple()

            setupCommandDispatcher()

            form.setModsUrl(settings.modsUrl)
            form.setMcExecutable(settings.mcExecutable)
            form.updateModList(settings.mcDataFolder)

            form.updateMessage(ResStrings.get("msg.welcome"))

            swing.doOutside {
                executeCommand(Commands.REFRESH_DOWNLOADABLE_MODS)
                executeCommand(Commands.REFRESH_DOWNLOADABLE_FORGES)
            }

        } // doLater
    }

    def setupCommandDispatcher() {
        cmdDispatcher = new SwingFormCommandDispatcher(settings, swing, form)

        // Launcher tab
        cmdDispatcher.putCommand(Commands.DELETE_SELECTED_MODS, new DeleteSelectedModsCommand())
        cmdDispatcher.putCommand(Commands.SELECT_ALL_MODS, new SelectAllModsCommand())
        cmdDispatcher.putCommand(Commands.UNSELECT_ALL_MODS, new UnselectAllModsCommand())

        cmdDispatcher.putCommand(Commands.ENABLE_SELECTED_MODS, new SetEnabledSelectedModsCommand(true))
        cmdDispatcher.putCommand(Commands.DISABLE_SELECTED_MODS, new SetEnabledSelectedModsCommand(false))
        cmdDispatcher.putCommand(Commands.ENABLE_ALL_MODS, new SetEnabledAllModsCommand(true))
        cmdDispatcher.putCommand(Commands.DISABLE_ALL_MODS, new SetEnabledAllModsCommand(false))

        cmdDispatcher.putCommand(Commands.REFRESH_MOD_LIST, new RefreshModListCommand())
        cmdDispatcher.putCommand(Commands.OPEN_INSTALLED_MODS_FOLDER, new OpenFileBrowserCommand(MinecraftDataFolder.getModsFolder(new File(settings.mcDataFolder))))
        cmdDispatcher.putCommand(Commands.OPEN_DISABLED_MODS_FOLDER, new OpenFileBrowserCommand(MinecraftDataFolder.getDisabledModsFolder(new File(settings.mcDataFolder))))
        cmdDispatcher.putCommand(Commands.LAUNCH_MINECRAFT, [new RefreshModListCommand(), new LaunchMinecraftCommand()])

        // Mods Downloader tab
        cmdDispatcher.putCommand(Commands.UPDATE_MODS_URL, [new UpdateModsUrlCommand(), new RefreshDownloadableModsCommand()])
        cmdDispatcher.putCommand(Commands.REFRESH_DOWNLOADABLE_MODS, new RefreshDownloadableModsCommand())
        cmdDispatcher.putCommand(Commands.DOWNLOAD_SELECTED_MODS, new DownloadModsCommand())

        // Settings tab
        cmdDispatcher.putCommand(Commands.CHANGE_MC_DATA_FOLDER, new ChangeMcDataFolderCommand())
        cmdDispatcher.putCommand(Commands.CHANGE_MC_EXECUTABLE, new ChangeMcExecutableCommand())
        cmdDispatcher.putCommand(Commands.REFRESH_DOWNLOADABLE_FORGES, new RefreshDownloadableForgesCommand())
        cmdDispatcher.putCommand(Commands.DOWNLOAD_FORGE, new DownloadForgeCommand())
        cmdDispatcher.putCommand(Commands.RUN_FORGE_INSTALLER, new RunForgeInstallerCommand())

        form.actionListener = { event ->
            log.debug("actionPerformed: $event.actionCommand")
            executeCommand(event.actionCommand)
        }

        form.tableModelListener = { event ->
            log.debug("tableChanged: firstRow=[$event.firstRow] lastRow=[$event.lastRow] column=[$event.column] type=[$event.type] src=[$event.source] event=[$event]")
        }
    }

    def executeCommand(cmd) {
        if (!cmdDispatcher.execute(cmd)) {
            log.warn("Unhandled action: $cmd")
            JOptionPane.showMessageDialog(form, ResStrings.get("msg.not.supported"))
        }
    }

    def initFolders() {
        FileUtils.mkdirs(MinecraftDataFolder.getModsFolder(settings.mcDataFolder))
        FileUtils.mkdirs(MinecraftDataFolder.getDisabledModsFolder(settings.mcDataFolder))
    }

}
