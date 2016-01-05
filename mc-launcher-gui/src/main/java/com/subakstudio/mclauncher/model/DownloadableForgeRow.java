package com.subakstudio.mclauncher.model;

/**
 * Created by yeoupooh on 1/6/16.
 */
public class DownloadableForgeRow implements IDownloadableRow {
    public String version;
    public String url;

    @Override
    public Object getValueAt(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return version;
            case 1:
                return url;
            default:
                return null;
        }
    }
}
