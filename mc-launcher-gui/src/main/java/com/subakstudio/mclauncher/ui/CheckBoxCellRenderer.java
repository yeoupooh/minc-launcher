package com.subakstudio.mclauncher.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by Thomas on 1/3/2016.
 */
public class CheckBoxCellRenderer extends DefaultTableCellRenderer {
    private final boolean value;

    public CheckBoxCellRenderer(boolean defaultValue) {
        this.value = defaultValue;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {
        if (column == 1) {
            return new JCheckBox();
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
