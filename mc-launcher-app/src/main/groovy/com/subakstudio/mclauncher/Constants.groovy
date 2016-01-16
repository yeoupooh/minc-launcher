package com.subakstudio.mclauncher

/**
 * Created by yeoupooh on 12/30/15.
 */
class Constants {
    static String USER_HOME = System.getProperty('user.home')
    static File MC_LAUNCHER_REPO_FOLDER = new File(USER_HOME, '.mclauncher')
    static File MC_LAUNCHER_REPO_FORGES_FOLDER = new File(MC_LAUNCHER_REPO_FOLDER, 'forges')
}
