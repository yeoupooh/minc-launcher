package com.subakstudio.mclauncher

import ch.qos.logback.classic.Level
import com.subakstudio.mclauncher.ui.McLauncherWindow
import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
public class McLauncher {
    public static void main(String[] args) {
        log.setLevel(Level.ALL)
        new McLauncherWindow()
    }
}
