package com.subakstudio.mclauncher.model

import com.subakstudio.mclauncher.util.McProps
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

    def load() {
        if (!file.exists()) {
            log.info("config file does not exist.")
            return false
        }

        json = new JsonSlurper().parseText(file.text)

        if (json.mcDataFolder == null) {
            log.info("mc data folder does not exist.")
            return false
        }

        mcDataFolder = json.mcDataFolder
        mcExecutable = json.mcExecutable
        modsUrl = json.modsUrl

        if (modsUrl == null) {
            modsUrl = McProps.get("url.json.mods")
        }

        log.info("config file is loaded: [$json]")

        return true
    }
}
