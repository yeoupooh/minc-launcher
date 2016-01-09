package com.subakstudio.mclauncher.cmd

/**
 * Created by Thomas on 1/9/2016.
 */
class InstallAllModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        form.checkAllMods()
        return true
    }
}
