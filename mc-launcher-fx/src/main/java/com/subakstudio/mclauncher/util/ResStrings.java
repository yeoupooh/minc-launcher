package com.subakstudio.mclauncher.util;

import java.util.ResourceBundle;

/**
 * Created by Thomas on 1/9/2016.
 */
public class ResStrings {
    private static ResourceBundle bundle = ResourceBundle.getBundle("strings");

    public static String get(String key) {
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        } else {
            return key;
        }
    }

}
