package com.subakstudio.mclauncher.model;

import com.subakstudio.mclauncher.MinecraftDataFolder;
import com.subakstudio.mclauncher.util.ResStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Thomas on 1/3/2016.
 */
public class ModsTableModel extends AbstractTableModel {
    public static final int COL_IS_ENABLED = 0;
    public static final int COL_NAME = 1;
    private final Logger log;
    /**
     * Holds all rows.
     */
    private List<ModsTableRow> mods = new ArrayList<ModsTableRow>();
    /**
     * Holds initial selected rows only. It is not changed after initially loaded.
     */
    private List<ModsTableRow> selected = new ArrayList<ModsTableRow>();
    /**
     * Holds modified rows only from selected rows.
     */
    private List<ModsTableRow> modified = new ArrayList<ModsTableRow>();

    public ModsTableModel() {
        log = LoggerFactory.getLogger(ModsTableModel.class);
    }

    public void setMcDataFolder(String path) {
        mods.clear();
        selected.clear();
        modified.clear();

        File dataFolder = new File(path);

        FileFilter dirFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        };

        if (dataFolder.exists()) {
            if (MinecraftDataFolder.getModsFolder(dataFolder).exists()) {
                File[] modFiles = MinecraftDataFolder.getModsFolder(dataFolder).listFiles(dirFilter);
                for (File file : modFiles) {
                    ModsTableRow row = new ModsTableRow();
                    row.file = file;
                    row.originChecked = row.newChecked = true;
                    mods.add(row);
                    selected.add(row);
                }
                log.info("mods files are loaded under mods folder.");
            } else {
                log.warn("No mods folder.");
            }
            if (MinecraftDataFolder.getDisabledModsFolder(dataFolder).exists()) {
                File[] modFiles = MinecraftDataFolder.getDisabledModsFolder(dataFolder).listFiles(dirFilter);
                for (File file : modFiles) {
                    ModsTableRow row = new ModsTableRow();
                    row.file = file;
                    row.originChecked = row.newChecked = false;
                    mods.add(row);
                }
                log.info("mods files are loaded under disabled mods folder.");
            } else {
                log.warn("No disabled mods folder.");
            }
        }

        Collections.sort(mods, new Comparator<ModsTableRow>() {
            @Override
            public int compare(ModsTableRow o1, ModsTableRow o2) {
                String str1 = o1.file.getName();
                String str2 = o2.file.getName();
                int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
                if (res == 0) {
                    res = str1.compareTo(str2);
                }
                return res;
            }
        });

        fireTableDataChanged();
    }

    public ModsTableRow getRow(int row) {
        return mods.get(row);
    }

    @Override
    public int getRowCount() {
        return mods.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case COL_NAME:
                return ResStrings.get("col.name");
            case COL_IS_ENABLED:
                return ResStrings.get("col.is.enabled");
            default:
                log.warn("Unknown columnIndex:" + columnIndex);
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == COL_IS_ENABLED ? true : false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModsTableRow row = mods.get(rowIndex);
        switch (columnIndex) {
            case COL_NAME:
                return row.file.getName();
            case COL_IS_ENABLED:
                return selected.contains(row);
        }

        return null;
    }

    /**
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == COL_IS_ENABLED) {
            log.debug("setValueAt: " + aValue);
            ModsTableRow row = mods.get(rowIndex);
            row.newChecked = Boolean.valueOf((Boolean) aValue);
            if (row.newChecked) {
                selected.add(row);
            } else {
                selected.remove(row);
            }

            if (row.originChecked != row.newChecked) {
                if (!modified.contains(row)) {
                    modified.add(row);
                }
            } else {
                if (modified.contains(row)) {
                    modified.remove(row);
                }
            }

            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_IS_ENABLED) {
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }

    public List<ModsTableRow> getSelected() {
        return selected;
    }

    public List<ModsTableRow> getModified() {
        return modified;
    }

    public void removeMods(int[] rowIndexes) {
        List<ModsTableRow> rows = new ArrayList<ModsTableRow>();
        for (int i = 0; i < rowIndexes.length; i++) {
            rows.add(mods.get(rowIndexes[i]));
        }

        for (ModsTableRow row : rows) {
            mods.remove(row);
            selected.remove(row);
            modified.remove(row);
        }

        fireTableDataChanged();
    }
}
