package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.util.PlatformUtils

import javax.swing.*

/**
 * Created by Thomas on 1/3/2016.
 */
class OpenFileBrowserCommand extends SwingFormCommand {
    def File path

    def OpenFileBrowserCommand(path) {
        this.path = path
    }

    @Override
    boolean execute() {
        try {
            PlatformUtils.openFileBrowser(path)
            return true
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(form, path.absolutePath + " not found.")
            return false
        }
    }
}
