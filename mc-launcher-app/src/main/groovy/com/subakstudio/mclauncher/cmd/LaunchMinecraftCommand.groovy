package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.Constants
import groovy.util.logging.Slf4j

import javax.swing.JOptionPane

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
            JOptionPane.showMessageDialog(form, "Minecraft Executable is not set.")
            return false
        }

        swing.doOutside {
            swing.doLater {
                form.updateMessage("Minecraft is launched.")
            }
            def output = [settings.mcExecutable].execute().text
            log.debug("output=$output")
        }

        return true
    }
}
