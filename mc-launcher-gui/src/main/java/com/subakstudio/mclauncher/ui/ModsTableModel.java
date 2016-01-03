package com.subakstudio.mclauncher.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Thomas on 1/3/2016.
 */
public class ModsTableModel extends AbstractTableModel {
    private final Logger log;
    private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
    private List<ModsRow> mods = new ArrayList<ModsRow>();
    private List<ModsRow> selected = new ArrayList<ModsRow>();

    public ModsTableModel() {
        log = LoggerFactory.getLogger(ModsTableModel.class);
    }

    public void setMcRoot(String path) {
        File[] files = new File(path).listFiles();
        mods.clear();
        selected.clear();
        for (File file : files) {
            ModsRow row = new ModsRow();
            row.file = file;
            row.checked = false;
            mods.add(row);
        }
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
            case 0:
                return ResourceBundle.getBundle("strings").getString("col.name");
            case 1:
                return ResourceBundle.getBundle("strings").getString("col.install");
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 ? true : false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModsRow row = mods.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.file.getName();
            case 1:
                return selected.contains(row);
        }

        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            log.debug("setValueAt: " + aValue);
            boolean bValue = Boolean.valueOf((Boolean) aValue);
            if (bValue) {
                selected.add(mods.get(rowIndex));
            } else {
                selected.remove(mods.get(rowIndex));
            }
            for (TableModelListener l : listeners) {
                l.tableChanged(new TableModelEvent(this));
            }
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) {
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }

    public java.util.List<ModsRow> getSelected() {
        return selected;
    }
}
