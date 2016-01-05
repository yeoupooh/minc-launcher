package com.subakstudio.mclauncher.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeoupooh on 1/6/16.
 */
public class DownloadableTableModel extends AbstractTableModel {
    private final String[] columnHeaders;
    private List<IDownloadableRow> data = new ArrayList<IDownloadableRow>();

    public DownloadableTableModel(String[] columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnHeaders[column];
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).getValueAt(columnIndex);
    }

    public void setData(List<IDownloadableRow> data) {
        this.data.clear();
        this.data.addAll(data);
        fireTableDataChanged();
    }
}
