package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.cmd.SwingFormCommand
import com.subakstudio.mclauncher.util.MinecraftUtils
import com.subakstudio.mclauncher.util.PlatformUtils

import javax.swing.JOptionPane

/**
 * Created by Thomas on 1/3/2016.
 */
class OpenInstalledModsFolderCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        try {
            PlatformUtils.openFileBrowser(MinecraftUtils.modsDir)
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(form, MinecraftUtils.modsDir + "not found.")
        }

        return true
    }
}
