package com.subakstudio.mclauncher.cmd

/**
 * Created by Thomas on 1/9/2016.
 */
class DisableAllModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        form.disableAllMods()
        return true
    }
}
