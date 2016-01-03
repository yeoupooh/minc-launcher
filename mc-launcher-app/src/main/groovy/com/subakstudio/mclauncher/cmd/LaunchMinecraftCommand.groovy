package com.subakstudio.mclauncher.cmd

import groovy.util.logging.Slf4j

import javax.swing.JOptionPane

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class LaunchMinecraftCommand extends SwingFormCommand{

    @Override
    def boolean execute() {
        form.selectedMods.each { mod ->
            log.debug("file=[$mod.file]")
        }

        if (form.mcExecutable.length() == 0) {
            JOptionPane.showMessageDialog(form, "Minecraft Executable is not set.")
            return false
        }

        return true
    }
}
