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
        def forges = form.selectedDownloadableForges
        if (forges.size > 0) {
            def forge = forges[0]
            log.debug("selected forge: $forge.fileName")
            File forgeFile = new File(Constants.MC_LAUNCHER_REPO_FORGES_FOLDER, forge.fileName as String)
            if (!forgeFile.exists()) {
                dialogBuilder.buildErrorWithResId("msg.no.forge.file.found").show()
            } else {
                def output = ['java', '-jar', forgeFile.absolutePath].execute().text
                log.debug("output=$output")
            }
        } else {
            dialogBuilder.buildErrorWithResId("msg.select.forge.to.run").show()
        }
        return true
    }
}
