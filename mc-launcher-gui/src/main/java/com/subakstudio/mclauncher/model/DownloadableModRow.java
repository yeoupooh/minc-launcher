package com.subakstudio.mclauncher.model;

/**
 * Created by yeoupooh on 1/6/16.
 */
public class DownloadableModRow implements IDownloadableRow {
    public String name;
    public String url;
    public String version;
    public String requiredVersion;

    @Override
    public Object getValueAt(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return name;
            case 1:
                return version;
            case 2:
                return requiredVersion;
            case 3:
                return url;
        }
        return null;
    }
}
