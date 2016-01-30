package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.MinecraftDataFolder
import com.subakstudio.mclauncher.util.FileUtils
import com.subakstudio.mclauncher.util.ResStrings
import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class RefreshModListCommand extends SwingFormCommand {

    @Override
    boolean execute() {

        form.updateMessage("Refreshing...")

        def i = 1, progress = 0
        form.modifiedMods.each { mod ->
            log.debug("file=[$mod.file] newChecked=[$mod.newChecked]")
            if (mod.newChecked) {
                FileUtils.move(mod.file, MinecraftDataFolder.getModsFolder(new File(settings.mcDataFolder)))
            } else {
                FileUtils.move(mod.file, MinecraftDataFolder.getDisabledModsFolder(new File(settings.mcDataFolder)))
            }
            progress = (i / form.modifiedMods.size()) * 100
            form.updateMessage(String.format("%s...%s%%", ResStrings.get("msg.refreshing"), progress))
            form.updateProgress((int) progress)
            i++
        }

        form.updateModList(settings.mcDataFolder);

        form.updateMessage(ResStrings.get("msg.refreshed"))
        form.updateProgress(100)

        return true;
    }
}
