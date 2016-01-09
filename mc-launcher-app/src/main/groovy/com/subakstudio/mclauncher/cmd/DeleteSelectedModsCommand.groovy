package com.subakstudio.mclauncher.cmd

import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/9/2016.
 */
@Slf4j
class DeleteSelectedModsCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        def selected = form.getSelectedMods()
        selected.each { mod ->
            log.debug("deleting...$mod.file")
            mod.file.delete()
            log.info("mod file deleted: $mod.file")
        }
        form.deleteSelectedMods()

        return true
    }
}
