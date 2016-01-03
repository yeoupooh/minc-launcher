package com.subakstudio.mclauncher.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Thomas on 1/3/2016.
 */
public class ModsTableCellEditor extends DefaultCellEditor implements ItemListener {
    public ModsTableCellEditor(JTextField textField) {
        super(textField);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
//    JCheckBox checkBox = new JCheckBox();
//    int editedRow;
//    int editedColumn;
//    JTable table;
//
//    @Override
//    public void itemStateChanged(ItemEvent e) {
//
//    }
//
//    class CheckBoxCellEditorListener implements ItemListener {
//        public void itemStateChanged(ItemEvent e) {
//            System.out.println("CheckBoxCellEditorListener");
//            Object source = e.getSource();
//            if (source instanceof AbstractButton == false) return;
//            boolean checked = e.getStateChange() == ItemEvent.SELECTED;
//            System.out.println("checked: " + (e.getStateChange()));
//
//            JCheckBox checkBox = (JCheckBox) (e.getSource());
//
//            ModsTableCellEditor checkBoxCellEditor = (ModsTableCellEditor) wordGroupTable.getCellEditor();
//            wordGroupTable.setValueAt(new Boolean(checked), checkBoxCellEditor.getRow(), checkBoxCellEditor.getColumn());
//            System.out.println("asdsad");
//        }
//    }
//
//    MyTableCellEditor(JTable table) {
//        super();
//        this.table = table;
//        checkBox.addItemListener(new CheckBoxCellEditorListener());
//        checkBox.setMultiClickThreshhold(0);
//    }
//
//    public Component getTableCellEditorComponent(JTable table,
//                                                 Object value, boolean isSelected, int row, int column) {
//        this.editedRow = row;
//        this.editedColumn = column;
//        checkBox.setBackground(UIManager.getColor("Table.selectionBackground"));
//        System.out.println("getTableCellEditorComponent");
//        return checkBox;
//    }
//
//    @Override
//    public Object getCellEditorValue() {
//        return new Boolean(checkBox.isSelected());
//    }
//
//    public JCheckBox getCheckbox() {
//        return checkBox;
//    }
//
//    public int getRow() {
//        return editedRow;
//    }
//
//    public int getColumn() {
//        return editedColumn;
//    }
//
//    public void SetRow(int row) {
//        editedRow = row;
//    }
//
//    public void SetColumn(int column) {
//        editedColumn = column;
//    }
}
