package com.subakstudio.mclauncher.util

import com.subakstudio.mclauncher.Constants
import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
class TempRepository {
    static def init() {
        def tempFolder = Constants.MC_LAUNCHER_TEMP_PATH
        if (!tempFolder.exists()) {
            tempFolder.mkdirs()
            log.info("temp folder[$tempFolder] is created.")
        } else {
            log.info("temp folder[$tempFolder] is already created.")
        }
    }
}
