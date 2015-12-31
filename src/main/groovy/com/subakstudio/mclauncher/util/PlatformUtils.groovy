package com.subakstudio.mclauncher.util

import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
class PlatformUtils {
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

        def osName = System.properties['os.name']
        if (osName.indexOf('Windows') > -1) {
            output = ['explorer', path].execute().text
        } else if (osName.indexOf('Linux') > -1) {
            output = ['/usr/bin/xdg-open', path].execute().text
        } else {
            output = "Unknown os[$osName]."
        }

        log.debug("output=$output")
    }
}
