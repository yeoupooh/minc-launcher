package com.subakstudio.mclauncher

import ch.qos.logback.classic.Level
import com.subakstudio.mclauncher.model.Settings
import com.subakstudio.mclauncher.ui.McLauncherController
import com.subakstudio.mclauncher.util.MinecraftUtils
import com.subakstudio.mclauncher.util.TempRepository
import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
public class McLauncher {
    public static void main(String[] args) {
        log.setLevel(Level.ALL)

        TempRepository.init()

        def configJsonFile = new File(Constants.MC_LAUNCHER_TEMP_PATH, "config.json")
        def settings = new Settings(configJsonFile)
        if (!settings.load()){
            settings.mcDataFolder = MinecraftUtils.mcDataFolder
        }
        settings.save()

        new McLauncherController(settings)
    }
}

