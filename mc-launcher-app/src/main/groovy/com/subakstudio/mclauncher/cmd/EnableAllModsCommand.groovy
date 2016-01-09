package com.subakstudio.mclauncher.cmd

/**
 * Created by Thomas on 1/9/2016.
 */
class EnableAllModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        form.enableAllMods()
        return true
    }
}
