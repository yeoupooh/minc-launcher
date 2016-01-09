package com.subakstudio.mclauncher.cmd

/**
 * Created by yeoupooh on 1/9/16.
 */
class SetEnabledAllModsCommand extends SwingFormCommand {
    boolean enabled

    def SetEnabledAllModsCommand(boolean enabled) {
        this.enabled = enabled
    }

    @Override
    boolean execute() {
        form.setEnabledAllMods(enabled)
        return true
    }
}
