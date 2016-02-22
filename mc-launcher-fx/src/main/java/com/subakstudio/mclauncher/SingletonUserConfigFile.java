package com.subakstudio.mclauncher;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by yeoupooh on 2/22/16.
 */
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

    public void load() {
        ObjectMapper om = new ObjectMapper();
        try {
            config = (UserConfig) om.readValue(
                    new File(Constants.MC_LAUNCHER_REPO_FOLDER, Constants.CONFIG_JSON_FILE_NAME), UserConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        ObjectMapper om = new ObjectMapper();
        try {
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
