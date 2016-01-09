package com.subakstudio.mclauncher.cmd

/**
 * Created by yeoupooh on 1/9/16.
 */
class SetEnabledSelectedModsCommand extends SwingFormCommand {
    boolean enabled

    def SetEnabledSelectedModsCommand(boolean enabled) {
        this.enabled = enabled
    }

    @Override
    boolean execute() {
        form.setEnabledSelectedMods(enabled)
        return true
    }
}
