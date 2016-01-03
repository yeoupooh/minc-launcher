package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.Constants
import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class RunForgeInstallerCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        def output = ['java', '-jar', new File(Constants.MC_LAUNCHER_TEMP_PATH, Constants.MCFG_JAR_NAME).absolutePath].execute().text
        log.debug("output=$output")

        return true
    }
}
