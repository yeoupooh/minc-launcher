package com.subakstudio.mclauncher.util

/**
 * Created by yeoupooh on 12/30/15.
 */
class MinecraftUtils {
    static String getMcDataFolder() {
        switch (PlatformUtils.os) {
            case PlatformUtils.OS.Windows:
                return new File(System.getenv('APPDATA'), '.minecraft').absolutePath
        }

        return new File(System.getProperty('user.home'), '.minecraft').absolutePath
    }
//
//    static String getModsDir() {
//        return new File(new File(getMcDataFolder()), 'mods')
//    }
//
//    static String getDownloadedModsDir() {
//        return new File(new File(getMcDataFolder()), 'mods-downloaded')
//    }
}
