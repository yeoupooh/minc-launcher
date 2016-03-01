package com.subakstudio.mclauncher.util;

import java.io.File;

/**
 * Created by Thomas on 1/3/2016.
 */
public class MinecraftDataFolder {

    public static final String MODES_FOLDER_NAME = "mods";
    public static final String DISABLED_MODS_FOLDER_NAME = "mods-disabled";

    public static File getModsFolder(String dataFolder) {
        return new File(dataFolder, MODES_FOLDER_NAME);
    }

    public static File getModsFolder(File dataFolder) {
        return new File(dataFolder, MODES_FOLDER_NAME);
    }

    public static File getDisabledModsFolder(String dataFolder) {
        return new File(dataFolder, DISABLED_MODS_FOLDER_NAME);
    }

    public static File getDisabledModsFolder(File dataFolder) {
        return new File(dataFolder, DISABLED_MODS_FOLDER_NAME);
    }
}
