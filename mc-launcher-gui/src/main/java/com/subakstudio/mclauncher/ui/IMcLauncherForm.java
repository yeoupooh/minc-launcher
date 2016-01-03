package com.subakstudio.mclauncher.ui;

import com.subakstudio.mclauncher.model.ModsTableRow;

import javax.swing.event.TableModelListener;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Thomas on 1/3/2016.
 */
public interface IMcLauncherForm extends ActionListener, TableModelListener {
    void setupUI();

    void updateMessage(String msg);

    void updateProgress(int progress);

    void setActionListener(ActionListener l);

    void setTableModelListener(TableModelListener l);

    void updateModList(String mcDataFolder);

    List<ModsTableRow> getModifiedMods();

    ModsTableRow getModAt(int rowIndex);
}
