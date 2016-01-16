package com.subakstudio.mclauncher.cmd

import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class LaunchMinecraftCommand extends SwingFormCommand {

    @Override
    def boolean execute() {

        form.modifiedMods.each { mod ->
            log.debug("file=[$mod.file] newChecked=[$mod.newChecked]")
        }

        if (settings.mcExecutable == null) {
            dialogBuilder.buildErrorWithResId("msg.minecraft.executable.not.set").show()
            return true
        }

        swing.doOutside {
            swing.doLater {
                form.updateMessage("Minecraft is launched.")
            }
            try {
                def output = [settings.mcExecutable].execute().text
                log.debug("output=$output")
            } catch (IOException e) {
                dialogBuilder.buildError(e.getMessage()).show()
            }
        }

        return true
    }
}
