package com.subakstudio.mclauncher;

import java.io.File;

/**
 * Created by yeoupooh on 2/22/16.
 */
public class Constants {
    public static final String CONFIG_JSON_FILE_NAME = "config.json";
    public static final String MC_LAUNCHER_CONFIG_JSON_FILE_NAME = "/mclauncher.config.json";
    public static String USER_HOME = System.getProperty("user.home");
    public static File MC_LAUNCHER_REPO_FOLDER = new File(USER_HOME, ".mclauncher");
    public static File MC_LAUNCHER_REPO_FORGES_FOLDER = new File(MC_LAUNCHER_REPO_FOLDER, "forges");
}
