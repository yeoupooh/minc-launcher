package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.util.PlatformUtils
import com.subakstudio.mclauncher.util.ResStrings

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
        } catch (FileNotFoundException e) {
            dialogBuilder.buildError(String.format("%s: %s", ResStrings.get("msg.folder.not.found", path.absolutePath))).show()
        }
        return true
    }
}
