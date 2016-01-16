package com.subakstudio.mclauncher.model;

import java.io.File;

/**
 * Created by Thomas on 1/3/2016.
 */
public class ModsTableRow {
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOriginChecked() {
        return originChecked;
    }

    public void setOriginChecked(boolean originChecked) {
        this.originChecked = originChecked;
    }

    public boolean isNewChecked() {
        return newChecked;
    }

    public void setNewChecked(boolean newChecked) {
        this.newChecked = newChecked;
    }

    File file;
    boolean checked;
    boolean originChecked;
    boolean newChecked;
}
