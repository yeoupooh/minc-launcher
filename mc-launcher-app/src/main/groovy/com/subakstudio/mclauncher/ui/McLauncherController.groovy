package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.Commands
import com.subakstudio.mclauncher.MinecraftDataFolder
import com.subakstudio.mclauncher.cmd.*
import com.subakstudio.mclauncher.model.DownloadableForgeRow
import com.subakstudio.mclauncher.model.DownloadableModRow
import com.subakstudio.mclauncher.model.Settings
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.OkHttpClientHelper
import com.subakstudio.mclauncher.util.ResStrings
import groovy.json.JsonSlurper
import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 1/1/16.
 */
@Slf4j
class McLauncherController {
    def swing = new SwingBuilder()
    def form
    Settings settings

    def McLauncherController(Settings settings) {
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

            form = new McLauncherSimple()

            def cmdDispatcher = new SwingFormCommandDispatcher(settings, swing, form)
            // Launcher tab
            cmdDispatcher.putCommand(Commands.REFRESH_MOD_LIST, new RefreshModListCommand())
            cmdDispatcher.putCommand(Commands.OPEN_INSTALLED_MODS_FOLDER, new OpenFileBrowserCommand(MinecraftDataFolder.getModsFolder(new File(settings.mcDataFolder))))
            cmdDispatcher.putCommand(Commands.OPEN_DISABLED_MODS_FOLDER, new OpenFileBrowserCommand(MinecraftDataFolder.getDisabledModsFolder(new File(settings.mcDataFolder))))
            cmdDispatcher.putCommand(Commands.LAUNCH_MINECRAFT, [new RefreshModListCommand(), new LaunchMinecraftCommand()])
            cmdDispatcher.putCommand(Commands.SELECT_ALL_MODS, new SelectAllModsCommand())
            cmdDispatcher.putCommand(Commands.UNSELECT_ALL_MODS, new UnselectAllModsCommand())
            cmdDispatcher.putCommand(Commands.DELETE_SELECTED_MODS, new DeleteSelectedModsCommand())
            cmdDispatcher.putCommand(Commands.INSTALL_ALL_MODS, new InstallAllModsCommand())
            cmdDispatcher.putCommand(Commands.UNINSTALL_ALL_MODS, new UninstallAllModsCommand())
            // Settings tab
            cmdDispatcher.putCommand(Commands.CHANGE_MC_DATA_FOLDER, new ChangeMcDataFolderCommand())
            cmdDispatcher.putCommand(Commands.CHANGE_MC_EXECUTABLE, new ChangeMcExecutableCommand())
            cmdDispatcher.putCommand(Commands.DOWNLOAD_FORGE, new DownloadForgeCommand())
            cmdDispatcher.putCommand(Commands.RUN_FORGE_INSTALLER, new RunForgeInstallerCommand())
            cmdDispatcher.putCommand(Commands.DOWNLOAD_MODS_PACK, new DownloadModsCommand())

            form.actionListener = { event ->
                log.debug("actionPerformed: $event.actionCommand")
                if (!cmdDispatcher.execute(event.actionCommand)) {
                    log.warn("Unhandled action: $event.actionCommand")
                }
            }

            form.tableModelListener = { event ->
                log.debug("tableChanged: firstRow=[$event.firstRow] lastRow=[$event.lastRow] column=[$event.column] type=[$event.type] src=[$event.source] event=[$event]")
            }

            form.setMcExecutable(settings.mcExecutable)
            form.updateModList(settings.mcDataFolder)

            form.updateMessage(ResStrings.get("msg.welcome"))

        } // doLater

        swing.doOutside {
            updateDownloadableMods()
            updateDownloadableForges()
        }
    }

    def updateDownloadableForges() {
        def http = new OkHttpClientHelper()
        def json = new JsonSlurper().parseText(http.downloadText('http://jsonblob.com/api/jsonBlob/568beb97e4b01190df470db4'))
        def list = new ArrayList<DownloadableForgeRow>()
        json.forges.each { forge ->
            def item = new DownloadableForgeRow()
            item.version = forge.version
            item.fileName = forge.fileName
            item.url = forge.url
            list.add(item)
        }
        swing.doLater {
            form.setDownloadableForges(list)
        }
    }

    def updateDownloadableMods() {
        def http = new OkHttpClientHelper()
        def json = new JsonSlurper().parseText(http.downloadText('http://jsonblob.com/api/jsonBlob/568bea5fe4b01190df470b79'))
        def list = new ArrayList<DownloadableModRow>()
        json.mods.each { mod ->
            def item = new DownloadableModRow()
            item.name = mod.name
            item.requiredVersion = mod.requiredVersion
            item.url = mod.url
            item.version = mod.version
            item.fileName = mod.fileName
            list.add(item)
        }
        swing.doLater {
            form.setDownloadableMods(list)
        }
    }

    def initFolders() {
        FileUtils.mkdirs(MinecraftDataFolder.getModsFolder(settings.mcDataFolder))
        FileUtils.mkdirs(MinecraftDataFolder.getDisabledModsFolder(settings.mcDataFolder))
    }

}
