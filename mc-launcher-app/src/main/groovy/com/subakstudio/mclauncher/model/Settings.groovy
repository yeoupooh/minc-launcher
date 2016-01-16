package com.subakstudio.mclauncher.model

import com.subakstudio.mclauncher.util.McProps
import com.subakstudio.mclauncher.util.MinecraftUtils
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class Settings {
    public String mcDataFolder
    public String mcExecutable
    public String modsUrl
    def file
    def json

    def Settings(file) {
        this.file = file
    }

    def save() {
        json = [mcDataFolder: mcDataFolder, mcExecutable: mcExecutable, modsUrl: modsUrl]
        file.write(new JsonBuilder(json).toPrettyString())
        log.info("config file saved: [$json]")
    }

    /**
     * Load settings from json file.
     *
     * @return true if it needs to be saved
     */
    def load() {
        boolean toBeSaved = false

        if (!file.exists()) {
            log.info("config file does not exist.")
            toBeSaved = true
        } else {
            json = new JsonSlurper().parseText(file.text)
            mcDataFolder = json.mcDataFolder
            mcExecutable = json.mcExecutable
            modsUrl = json.modsUrl
            log.info("config file is loaded: [$json]")
        }

        if (mcDataFolder == null) {
            log.info("mc data folder does not exist.")
            mcDataFolder = MinecraftUtils.findMcDataFolder()
            if (mcDataFolder != null) {
                toBeSaved = true
            }
        }

        if (mcExecutable == null) {
            log.info("mc executable does not exist.")
            mcExecutable = MinecraftUtils.findMcExecutable()
            if (mcExecutable != null) {
                toBeSaved = true
            }
        }

        if (modsUrl == null) {
            log.info("mods url not found.")
            modsUrl = McProps.get("url.json.mods")
            toBeSaved = true
        }

        return toBeSaved
    }
}
