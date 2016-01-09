package com.subakstudio.mclauncher.util;

import java.util.ResourceBundle;

/**
 * Created by Thomas on 1/9/2016.
 */
public class McProps {
    public static String get(String key) {
        return ResourceBundle.getBundle("mclauncher").getString(key);
    }
}
