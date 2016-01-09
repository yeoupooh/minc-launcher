package com.subakstudio.mclauncher.cmd

/**
 * Created by Thomas on 1/9/2016.
 */
class UninstallAllModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        form.uncheckAllMods()
        return true
    }
}
