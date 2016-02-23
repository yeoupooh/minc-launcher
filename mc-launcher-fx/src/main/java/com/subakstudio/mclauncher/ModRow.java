package com.subakstudio.mclauncher;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import java.io.File;

/**
 * Created by yeoupooh on 2/23/16.
 */
public class ModRow {

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public boolean isOriginChecked() {
        return originChecked;
    }

    public void setOriginChecked(boolean originChecked) {
        this.originChecked = originChecked;
    }

    public ObservableValue<Boolean> onProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public boolean isChecked() {
        return checked.get();
    }

    @Override
    public String toString() {
        return file.getName();
    }

    private File file;
    private boolean originChecked;
    private BooleanProperty checked = new SimpleBooleanProperty();
}
