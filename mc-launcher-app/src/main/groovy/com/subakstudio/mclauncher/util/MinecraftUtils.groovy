package com.subakstudio.mclauncher.util

import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
class MinecraftUtils {
    static String findMcDataFolder() {
        File mcDataFolder;
        switch (PlatformUtils.os) {
            case PlatformUtils.OS.Windows:
                mcDataFolder = new File(System.getenv('APPDATA'), '.minecraft')
                break

            case PlatformUtils.OS.Mac:
                mcDataFolder = new File(System.getProperty("user.home") + "/Library/Application Support/minecraft")
                break

            default:
                mcDataFolder = new File(System.getProperty('user.home'), '.minecraft')
        }

        if (!mcDataFolder.exists()) {
            log.warn("No mc data folder found: $mcDataFolder.absolutePath")
            return null
        }

        return mcDataFolder.absolutePath
    }

    static String findMcExecutable() {
        File mcExecutable

        switch (PlatformUtils.os) {
            case PlatformUtils.OS.Windows:
                def mcFolder = new File(System.getenv('ProgramFiles(X86)'), 'Minecraft')
                mcExecutable = new File(mcFolder, 'Minecraft.exe')
                if (!mcExecutable.exists()) {
                    mcExecutable = new File(mcFolder, 'MinecraftLauncher.exe')
                }
                break

            case PlatformUtils.OS.Mac:
                mcExecutable = new File('/Applications/Minecraft.app/Contents/MacOS/launcher')
                break
        }

        if (mcExecutable == null || !mcExecutable.exists()) {
            log.warn("No mc executable found: $mcExecutable")
            return null
        }

        return mcExecutable.absolutePath
    }
}
