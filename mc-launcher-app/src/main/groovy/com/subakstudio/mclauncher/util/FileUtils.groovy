package com.subakstudio.mclauncher.util

import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 1/1/16.
 */
@Slf4j
class FileUtils {
    static def mkdirs(String path) {
        def p = new File(path)
        if (!p.exists()) {
            p.mkdirs()
            log.info("$path is created.")
        }
    }

    static def delete(File file) {
        log.debug("delete file[$file]")
        file.delete()
    }

    static def copy(File src, File dest) {
        def input = src.newDataInputStream()
        def output = dest.newDataOutputStream()

        output << input

        input.close()
        output.close()
    }
}
