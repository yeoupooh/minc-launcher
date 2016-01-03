package com.subakstudio.mclauncher.model;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeoupooh on 1/1/16.
 */
public class FileListModel implements ListModel {
    private File[] files;
    private List<ListDataListener> listeners = new ArrayList<ListDataListener>();
    private String path;

    public FileListModel() {
    }

    public void reload() {
        reload(path);
    }

    public void reload(String path) {
        this.path = path;
        files = (new File(path).listFiles());
    }

    /**
     * Returns the length of the list.
     *
     * @return the length of the list
     */
    @Override
    public int getSize() {
        if (files != null) {
            return files.length;
        }
        return 0;
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public Object getElementAt(int index) {
        return files[index].getName();
    }

    /**
     * Adds a listener to the list that's notified each time a change
     * to the data model occurs.
     *
     * @param l the <code>ListDataListener</code> to be added
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    /**
     * Removes a listener from the list that's notified each time a
     * change to the data model occurs.
     *
     * @param l the <code>ListDataListener</code> to be removed
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }
}
