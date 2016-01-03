package com.subakstudio.mclauncher;

import ch.qos.logback.classic.Level;
import com.subakstudio.mclauncher.ui.McLauncherSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yeoupooh on 1/2/16.
 */
public class McLauncherFormJavaRunner {
    public static void main(String[] args) {
        final Logger log = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        if (log instanceof ch.qos.logback.classic.Logger) {
            ch.qos.logback.classic.Logger logbackLog = (ch.qos.logback.classic.Logger) log;
            logbackLog.setLevel(Level.ALL);
            System.out.println("set log level");
        }
        log.info("starting...");

//        McModsManagerForm form = new McModsManagerForm();
        McLauncherSimple form = new McLauncherSimple();
//        McLauncherForm form = new McLauncherForm();
    }
}
