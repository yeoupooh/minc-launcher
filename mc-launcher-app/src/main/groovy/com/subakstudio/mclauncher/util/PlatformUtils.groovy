package com.subakstudio.mclauncher.util

import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
class PlatformUtils {
    enum OS {
        Windows, Linux, Mac, Unknown
    }

    static OS getOs() {
        def osName = System.properties['os.name']
        if (osName.indexOf('Windows') > -1) {
            return OS.Windows
        } else if (osName.indexOf('Linux') > -1) {
            return OS.Linux
        }

        return OS.Unknown
    }

    static def openFileBrowser(File file) throws FileNotFoundException {
        openFileBrowser(file.absolutePath)
    }

    static def openFileBrowser(String path) throws FileNotFoundException {
        log.debug("open file browser at $path")

        if (!new File(path).exists()) {
            throw new FileNotFoundException(path)
        }

        def output
//        def sysProps = System.properties
//        sysProps.each { key, value ->
//            log.debug("$key=$value")
//        }

        switch (getOs()) {
            case OS.Windows:
                output = ['explorer', path].execute().text
                break

            case OS.Linux:
                output = ['/usr/bin/xdg-open', path].execute().text
                break

            default:
                output = "Unknown os[$os]."
        }

        log.debug("output=$output")
    }

}
