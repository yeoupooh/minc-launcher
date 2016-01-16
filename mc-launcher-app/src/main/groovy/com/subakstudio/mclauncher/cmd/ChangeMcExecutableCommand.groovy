package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.util.PlatformUtils
import groovy.util.logging.Slf4j

import javax.swing.JFileChooser

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class ChangeMcExecutableCommand extends SwingFormCommand {
    @Override
    boolean execute() {
        def fileFilter
        if (PlatformUtils.os == PlatformUtils.OS.Windows) {
            fileFilter = [
                    getDescription: { -> "*.exe" },
                    accept        : { file -> file.toString() ==~ /.*?\.exe/ || file.isDirectory() }
            ] as javax.swing.filechooser.FileFilter
        } else {
            fileFilter = [
                    getDescription: { -> "*" },
                    accept        : { file -> true }
            ] as javax.swing.filechooser.FileFilter
        }

        log.debug("filter=[$fileFilter]")

        def currDir = settings.mcExecutable != null && new File(settings.mcExecutable).exists() ? new File(settings.mcExecutable) : null

        def fileChooser = swing.fileChooser(
                dialogTitle: "Choose Minecraft executable file",
                fileSelectionMode: JFileChooser.FILES_ONLY,
                //the file filter must show also directories, in order to be able to look into them
                fileFilter: fileFilter,
                currentDirectory: currDir
        )

        def ret = fileChooser.showOpenDialog()
        log.debug("ret=[$ret] file=[$fileChooser.selectedFile]")
        if (ret == JFileChooser.APPROVE_OPTION) {
            settings.mcExecutable = fileChooser.selectedFile
            settings.save()
            form.setMcExecutable(settings.mcExecutable)
        }

        return true
    }
}
