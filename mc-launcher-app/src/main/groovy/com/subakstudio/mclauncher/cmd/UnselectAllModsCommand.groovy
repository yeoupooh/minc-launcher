package com.subakstudio.mclauncher.cmd

/**
 * Created by Thomas on 1/9/2016.
 */
class UnselectAllModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        form.unselectAllMods()
        return true
    }
}
