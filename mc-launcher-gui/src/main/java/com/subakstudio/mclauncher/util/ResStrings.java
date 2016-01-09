package com.subakstudio.mclauncher.util;

import java.util.ResourceBundle;

/**
 * Created by Thomas on 1/9/2016.
 */
public class ResStrings {
    public static String get(String key) {
        if (ResourceBundle.getBundle("strings").containsKey(key)) {
            return ResourceBundle.getBundle("strings").getString(key);
        } else {
            return key;
        }
    }
}
