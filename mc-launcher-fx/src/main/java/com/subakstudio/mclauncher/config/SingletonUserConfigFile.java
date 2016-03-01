package com.subakstudio.mclauncher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.subakstudio.mclauncher.Constants;
import com.subakstudio.mclauncher.util.MinecraftUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * Created by yeoupooh on 2/22/16.
 */
@Slf4j
public class SingletonUserConfigFile {
    private static SingletonUserConfigFile instance;
    private UserConfig config;

    // Prevent public constructor
    private SingletonUserConfigFile() {

    }

    public static SingletonUserConfigFile getInstance() {
        if (instance == null) {
            instance = new SingletonUserConfigFile();
        }
        return instance;
    }

    public void initWithDefaults() {
        config = new UserConfig();
        config.setFormat(UserConfig.FORMAT_2_0);
        config.setMcDataFolder(MinecraftUtils.findMcDataFolder());
        config.setMcExecutable(MinecraftUtils.findMcExecutable());
        config.setModsUrl(SingletonMcLauncherConfigFile.getConfig().getModsUrl());
    }

    public void load() {
        File configFile = new File(Constants.MC_LAUNCHER_REPO_FOLDER, Constants.CONFIG_JSON_FILE_NAME);
        if (!configFile.exists()) {
            log.info("Initializing config file.");
            initWithDefaults();
            save();
        }
        ObjectMapper om = new ObjectMapper();
        try {
            config = (UserConfig) om.readValue(configFile, UserConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (config.getFormat() == null || !config.getFormat().equals(UserConfig.FORMAT_2_0)) {
            log.info("Updating config file to " + UserConfig.FORMAT_2_0);
            // Update format, mods url
            config.setFormat(UserConfig.FORMAT_2_0);
            config.setModsUrl(SingletonMcLauncherConfigFile.getConfig().getModsUrl());
            save();
        }
    }

    public void save() {
        ObjectMapper om = new ObjectMapper();
        try {
            om.enable(SerializationFeature.INDENT_OUTPUT);
            om.writeValue(new File(
                    Constants.MC_LAUNCHER_REPO_FOLDER, Constants.CONFIG_JSON_FILE_NAME), config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserConfig getConfig() {
        return getInstance().config;
    }
}
