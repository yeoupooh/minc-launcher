package com.subakstudio.mclauncher.model

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class Settings {
    public String mcRoot
    public String mcExecutable
    public def selectedMods = []
    def file
    def json

    def Settings(file) {
        this.file = file
    }

    def save() {
        json = [mcRoot: mcRoot, mcExecutable: mcExecutable, selectedMods: selectedMods]
        file.write(new JsonBuilder(json).toPrettyString())
        log.info("config file saved: [$json]")
    }

    def load() {
        if (!file.exists()) {
            log.info("config file does not exist.")
            return false
        }

        json = new JsonSlurper().parseText(file.text)
        log.info("config file is loaded: [$json]")

        return true
    }
}
