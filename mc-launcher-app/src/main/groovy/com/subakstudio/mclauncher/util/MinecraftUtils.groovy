package com.subakstudio.mclauncher.util

/**
 * Created by yeoupooh on 12/30/15.
 */
class MinecraftUtils {
    static String getMcRoot() {
        return new File(System.getProperty('user.home'), '.minecraft').absolutePath
    }

    static String getModsDir() {
        return new File(new File(getMcRoot()), 'mods')
    }

    static String getDownloadedModsDir() {
        return new File(new File(getMcRoot()), 'mods-downloaded')
    }
}
