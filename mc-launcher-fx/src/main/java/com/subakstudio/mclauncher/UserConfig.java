package com.subakstudio.mclauncher;

import java.io.File;

/**
 * Created by yeoupooh on 2/22/16.
 */
public class UserConfig {
    public static final String MODES_FOLDER_NAME = "mods";
    public static final String DISABLED_MODS_FOLDER_NAME = "mods-disabled";

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMcDataFolder() {
        return mcDataFolder;
    }

    public void setMcDataFolder(String mcDataFolder) {
        this.mcDataFolder = mcDataFolder;
    }

    public String getMcExecutable() {
        return mcExecutable;
    }

    public void setMcExecutable(String mcExecutable) {
        this.mcExecutable = mcExecutable;
    }

    public String getModsUrl() {
        return modsUrl;
    }

    public void setModsUrl(String modsUrl) {
        this.modsUrl = modsUrl;
    }

    public File getModsFolder() {
        return new File(mcDataFolder, MODES_FOLDER_NAME);
    }

    public File getDisabledModsFolder() {
        return new File(mcDataFolder, DISABLED_MODS_FOLDER_NAME);
    }

    private String format;
    private String mcDataFolder;
    private String mcExecutable;
    private String modsUrl;
}
