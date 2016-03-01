package com.subakstudio.mclauncher;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;

/**
 * Created by yeoupooh on 2/22/16.
 */
public class UserConfig {
    public static final String MODES_FOLDER_NAME = "mods";
    public static final String DISABLED_MODS_FOLDER_NAME = "mods-disabled";
    public static final String FORMAT_2_0 = "2.0";

    private String format;
    private String mcDataFolder;
    private String mcExecutable;
    private String modsUrl;

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

    @JsonIgnore
    public File getModsFolder() {
        return new File(mcDataFolder, MODES_FOLDER_NAME);
    }

    @JsonIgnore
    public File getDisabledModsFolder() {
        return new File(mcDataFolder, DISABLED_MODS_FOLDER_NAME);
    }
}
