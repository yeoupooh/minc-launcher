package com.subakstudio.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.subakstudio.util.PlatformUtils.OS.*;

/**
 * Created by Thomas on 2/25/2016.
 */
@Slf4j
public class PlatformUtils {
    public enum OS {
        Windows, Linux, Mac, Unknown
    }

    public static OS getOs() {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") > -1) {
            return Windows;
        } else if (osName.indexOf("Linux") > -1) {
            return Linux;
        } else if (osName.indexOf("Mac") > -1) {
            return Mac;
        }

        log.warn("Unknown OS: $osName");

        return Unknown;
    }

    public static void openFileBrowser(File file) throws IOException {
        openFileBrowser(file.getAbsolutePath());
    }

    public static void openFileBrowser(String path) throws IOException {
        log.debug("open file browser at " + path);

        if (!new File(path).exists()) {
            throw new FileNotFoundException(path);
        }

        ProcessBuilder pb = new ProcessBuilder();

        switch (getOs()) {
            case Windows:
                pb.command("explorer", path);
                break;

            case Linux:
                pb.command("/usr/bin/xdg-open", path);
                break;

            case Mac:
                pb.command("open", path);
                break;

            default:
                throw new IOException("Unknown OS");
        }

        log.debug("pb=" + pb.command());

        // For Debug
//        pb.redirectError(ProcessBuilder.Redirect.appendTo(new File("error.log")));
//        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("output.log")));

        pb.start();
    }

}
