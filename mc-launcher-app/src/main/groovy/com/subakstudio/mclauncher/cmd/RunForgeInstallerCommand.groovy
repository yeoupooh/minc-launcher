package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.Constants
import com.subakstudio.mclauncher.util.ResStrings
import groovy.util.logging.Slf4j

import javax.swing.JComponent
import javax.swing.JOptionPane
import java.awt.Component

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
            def output = ['java', '-jar', new File(Constants.MC_LAUNCHER_TEMP_PATH, forge.fileName as String).absolutePath].execute().text
            log.debug("output=$output")
        } else {
            JOptionPane.showMessageDialog(form as Component, ResStrings.get("msg.select.forge.to.run"))
        }
        return true
    }
}
