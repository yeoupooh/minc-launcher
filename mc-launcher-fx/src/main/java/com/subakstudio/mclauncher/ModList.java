package com.subakstudio.mclauncher;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.emptyObservableSet;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableSet;

/**
 * Created by yeoupooh on 2/23/16.
 */
@Slf4j
public class ModList {
    private ObservableList<ModRow> mods = observableArrayList();
    private ObservableSet<ModRow> modified = observableSet();
    private ObservableSet<ModRow> enabled = observableSet();

    public ObservableList<ModRow> getMods() {
        return mods;
    }

    public void add(ModRow row) {
        mods.add(row);
        if (row.isChecked()) {
            enabled.add(row);
        }
        row.onProperty().addListener((obs, wasOn, isNowOn) -> {
//            log.debug(row.getFile().getName() + " changed on state from " + wasOn + " to " + isNowOn);
            if (row.isOriginChecked() != isNowOn) {
                modified.add(row);
            } else {
                modified.remove(row);
            }
            if (isNowOn) {
                if (!enabled.contains(row)) {
                    enabled.add(row);
                }
            } else {
                enabled.remove(row);
            }
        });
    }

    public ObservableSet<ModRow> getModfied() {
        return modified;
    }

    public ObservableSet<ModRow> getEnabled() {
        return enabled;
    }

    public void clear() {
        mods.clear();
        modified.clear();
        enabled.clear();
    }
}
