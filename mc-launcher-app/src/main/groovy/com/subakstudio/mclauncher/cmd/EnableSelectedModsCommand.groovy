package com.subakstudio.mclauncher.cmd

/**
 * Created by yeoupooh on 1/9/16.
 */
class EnableSelectedModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        form.enableSelectedMods()
        return true
    }
}
