package com.subakstudio.mclauncher;

import java.io.File;

/**
 * Created by Thomas on 1/3/2016.
 */
public class MinecraftDataFolder {
    public static File getModsFolder(File dataFolder) {
        return new File(dataFolder, "mods");
    }

    public static File getDisabledModsFolder(File dataFolder) {
        return new File(dataFolder, "mods-disabled");
    }
}
