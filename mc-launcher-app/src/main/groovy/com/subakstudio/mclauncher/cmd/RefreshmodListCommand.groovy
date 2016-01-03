package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.util.MinecraftUtils

/**
 * Created by Thomas on 1/3/2016.
 */
class RefreshModListCommand extends SwingFormCommand {

    @Override
    boolean execute() {
        form.updateModList(MinecraftUtils.mcDataFolder);

        return true;
    }
}
