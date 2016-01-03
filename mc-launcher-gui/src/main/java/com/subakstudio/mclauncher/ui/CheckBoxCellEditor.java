package com.subakstudio.mclauncher.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import java.awt.*;
import java.util.EventObject;

/**
 * Created by Thomas on 1/3/2016.
 */
public class CheckBoxCellEditor extends DefaultCellEditor {
    private final Logger log;

    public CheckBoxCellEditor() {
        super(new JTextField());
        log = LoggerFactory.getLogger(CheckBoxCellEditor.class);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return new Checkbox();
    }

    @Override
    public Object getCellEditorValue() {
        log.debug("getCellEditorvalue");
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        log.debug("isCellEditable");
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        log.debug("shouldSelectCell");
        return false;
    }

    @Override
    public boolean stopCellEditing() {
        log.debug("stopCellEditing");
        return false;
    }

    @Override
    public void cancelCellEditing() {
        log.debug("cancelCellEditing");
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {

    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {

    }
}
