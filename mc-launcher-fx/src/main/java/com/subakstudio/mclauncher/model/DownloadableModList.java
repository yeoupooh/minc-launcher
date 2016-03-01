package com.subakstudio.mclauncher.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeoupooh on 2/19/16.
 */
public class DownloadableModList {
    private String format;
    private String title;
    private String version;
    private String updated;
    private List<DownloadableModRow> mods = new ArrayList<>();

    public List<DownloadableModRow> getMods() {
        return mods;
    }
}
