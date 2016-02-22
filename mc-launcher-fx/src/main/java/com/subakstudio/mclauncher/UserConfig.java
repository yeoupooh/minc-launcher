package com.subakstudio.mclauncher;

/**
 * Created by yeoupooh on 2/22/16.
 */
public class UserConfig {
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

    private String format;
    private String mcDataFolder;
    private String mcExecutable;
    private String modsUrl;
}
