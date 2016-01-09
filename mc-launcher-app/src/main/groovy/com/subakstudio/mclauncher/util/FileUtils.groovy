package com.subakstudio.mclauncher.util

import groovy.util.logging.Slf4j
import org.apache.commons.io.FilenameUtils

/**
 * Created by yeoupooh on 1/1/16.
 */
@Slf4j
class FileUtils {
    static def mkdirs(String path) {
        def p = new File(path)
        mkdirs(p)
    }

    static def mkdirs(File p) {
        if (!p.exists()) {
            p.mkdirs()
            log.info("$p is created.")
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

    static def move(File srcFile, File destFolder) {
        boolean moved = srcFile.renameTo(new File(destFolder, srcFile.getName()))
        log.info("$srcFile is moved to $destFolder: $moved")
    }

    /**
     * Return file name from url if file name doesn't exist. Otherwise return given filename.
     *
     * @param fileName
     * @param url
     * @return
     */
    static def getFileNameFromUrl(String fileName, String url) {
        if (fileName != null && fileName.length() > 0) {
            return fileName
        }

        if (url != null) {
            return URLDecoder.decode(FilenameUtils.getName(url), "utf-8")
        }

        return null
    }
}
