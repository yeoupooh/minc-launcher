package com.subakstudio.mclauncher.cmd

import groovy.util.logging.Slf4j

import javax.swing.JFileChooser

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class ChangeMcDataFolderCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        def currDir = settings.mcDataFolder != null && new File(settings.mcDataFolder).exists() ? new File(settings.mcDataFolder) : null

        def fileChooser = swing.fileChooser(
                dialogTitle: "Choose Minecraft executable file",
                fileSelectionMode: JFileChooser.DIRECTORIES_ONLY,
                currentDirectory: currDir
        )

        def ret = fileChooser.showOpenDialog()
        log.debug("ret=[$ret] file=[$fileChooser.selectedFile]")
        if (ret == JFileChooser.APPROVE_OPTION) {
            settings.mcDataFolder = fileChooser.selectedFile
            settings.save()
            form.setMcDataFolder(settings.mcDataFolder)
        }

        return true
    }
}
