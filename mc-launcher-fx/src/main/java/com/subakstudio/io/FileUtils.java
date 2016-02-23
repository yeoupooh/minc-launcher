package com.subakstudio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Created by yeoupooh on 2/23/16.
 */
@Slf4j
public class FileUtils {
    public static void move(File srcFile, File destFolder) {
        boolean moved = srcFile.renameTo(new File(destFolder, srcFile.getName()));
        log.info(String.format("%s is moved to %s: %s", srcFile, destFolder, moved));

    }
}

