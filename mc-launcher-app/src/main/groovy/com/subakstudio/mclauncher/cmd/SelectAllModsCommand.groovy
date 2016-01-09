package com.subakstudio.mclauncher.cmd

/**
 * Created by Thomas on 1/9/2016.
 */
class SelectAllModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        form.selectAllMods()
        return true
    }
}
