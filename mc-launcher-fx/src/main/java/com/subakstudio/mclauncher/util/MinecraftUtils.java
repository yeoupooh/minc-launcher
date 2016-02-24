package com.subakstudio.mclauncher.util;

import com.subakstudio.util.PlatformUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Created by Thomas on 2/25/2016.
 */
@Slf4j
public class MinecraftUtils {
    public static String findMcDataFolder() {
        File mcDataFolder;
        switch (PlatformUtils.getOs()) {
            case Windows:
                mcDataFolder = new File(System.getenv("APPDATA"), ".minecraft");
                break;

            case Mac:
                mcDataFolder = new File(System.getProperty("user.home") + "/Library/Application Support/minecraft");
                break;

            default:
                mcDataFolder = new File(System.getProperty("user.home"), ".minecraft");
        }

        if (!mcDataFolder.exists()) {
            log.warn("No mc data folder found: $mcDataFolder.absolutePath");
            return null;
        }

        return mcDataFolder.getAbsolutePath();
    }

    public static String findMcExecutable() {
        File mcExecutable = null;

        switch (PlatformUtils.getOs()) {
            case Windows:
                File mcFolder = new File(System.getenv("ProgramFiles(X86)"), "Minecraft");
                mcExecutable = new File(mcFolder, "Minecraft.exe");
                if (!mcExecutable.exists()) {
                    mcExecutable = new File(mcFolder, "MinecraftLauncher.exe");
                }
                break;

            case Mac:
                mcExecutable = new File("/Applications/Minecraft.app/Contents/MacOS/launcher");
                break;
        }

        if (mcExecutable == null || !mcExecutable.exists()) {
            log.warn("No mc executable found: $mcExecutable");
            return null;
        }

        return mcExecutable.getAbsolutePath();
    }
}
