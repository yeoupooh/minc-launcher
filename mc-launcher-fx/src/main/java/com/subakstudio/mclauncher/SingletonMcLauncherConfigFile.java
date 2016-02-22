package com.subakstudio.mclauncher;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by yeoupooh on 2/22/16.
 */
public class SingletonMcLauncherConfigFile {
    private static SingletonMcLauncherConfigFile instance;
    private McLauncherConfig config;

    // Prevent public constructor
    private SingletonMcLauncherConfigFile() {

    }

    public static SingletonMcLauncherConfigFile getInstance() {
        if (instance == null) {
            instance = new SingletonMcLauncherConfigFile();
        }
        return instance;
    }

    public void load() {
        ObjectMapper om = new ObjectMapper();
        try {
            config = (McLauncherConfig) om.readValue(getClass().getResourceAsStream(Constants.MC_LAUNCHER_CONFIG_JSON_FILE_NAME), McLauncherConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static McLauncherConfig getConfig() {
        return getInstance().config;
    }
}
