package com.subakstudio.mclauncher.ui;

import com.subakstudio.mclauncher.model.IDownloadableRow;
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

    List<ModsTableRow> getSelectedMods();

    List<ModsTableRow> getModifiedMods();

    void selectAllMods();

    void unselectleAllMods();

    void setEnabledAllMods(boolean enabled);

    void setEnabledSelectedMods(boolean enabled);

    void deleteSelectedMods();

    void removeModAt(int rowIndex);

    ModsTableRow getModAt(int rowIndex);

    void setDownloadableMods(List<IDownloadableRow> mods);

    List<IDownloadableRow> getSelectedDownloadableMods();

    void setDownloadableForges(List<IDownloadableRow> forges);

    List<IDownloadableRow> getSelectedDownloadableForges();
}
