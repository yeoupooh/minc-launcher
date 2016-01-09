package com.subakstudio.mclauncher.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.subakstudio.mclauncher.Commands;
import com.subakstudio.mclauncher.model.*;
import com.subakstudio.mclauncher.util.ResStrings;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by yeoupooh on 1/3/16.
 */
public class McLauncherSimple extends BaseMcLauncherFrame {
    private final ModsTableModel modsTableModel;
    private JTabbedPane tabbedPane1;
    private JPanel contentPane;
    private JTable modsTable;
    private JButton launchMinecraftWithForgeButton;
    private JButton refreshButton;
    private JTextField mcDataFolderTextField;
    private JButton changeMcDataFolderButton;
    private JButton openModsFolderButton;
    private JTextField mcExecTextField;
    private JButton changeMcExecButton;
    private JButton downloadMinecraftForgeInstallerButton;
    private JButton runMinecraftForgeInstallerButton;
    private JLabel selectedLabel;
    private JButton selectAllButton;
    private JButton unselectAllButton;
    private JButton downloadModsPackButton;
    private JProgressBar progressBar2;
    private JLabel messageLabel2;
    private JButton openDisabledModsFolderButton;
    private JTable downloadableModTable;
    private JTable downloadableForgeTable;
    private JButton deleteSelectedButton;
    private JButton installAllButton;
    private JButton uninstallAllButton;
    private DownloadableTableModel downloadableForgeTableModel;
    private DownloadableTableModel downloadableModTableModel;

    @Override
    public JPanel getRootContentPane() {
        return contentPane;
    }

    public McLauncherSimple() {
        setupUI();

        modsTableModel = new ModsTableModel();
        modsTable.setModel(modsTableModel);
        modsTable.setDefaultRenderer(Boolean.class, new CheckBoxCellRenderer(false));
        modsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                selectedLabel.setText(
                        String.format("%s: %s",
                                ResStrings.get("label.enabled.mods"),
                                modsTableModel.getSelected().size()
                        )
                );
            }
        });
        modsTableModel.addTableModelListener(this);

        setupDownloadableTables();

        // Launcher tab
        mapAction(refreshButton, Commands.REFRESH_MOD_LIST);
        mapAction(selectAllButton, Commands.SELECT_ALL_MODS);
        mapAction(unselectAllButton, Commands.UNSELECT_ALL_MODS);
        mapAction(deleteSelectedButton, Commands.DELETE_SELECTED_MODS);

        mapAction(installAllButton, Commands.INSTALL_ALL_MODS);
        mapAction(uninstallAllButton, Commands.UNINSTALL_ALL_MODS);

        mapAction(openModsFolderButton, Commands.OPEN_INSTALLED_MODS_FOLDER);
        mapAction(openDisabledModsFolderButton, Commands.OPEN_DISABLED_MODS_FOLDER);

        // Settings tab
        mapAction(changeMcDataFolderButton, Commands.CHANGE_MC_DATA_FOLDER);
        mapAction(changeMcExecButton, Commands.CHANGE_MC_EXECUTABLE);
        mapAction(launchMinecraftWithForgeButton, Commands.LAUNCH_MINECRAFT);
        mapAction(downloadMinecraftForgeInstallerButton, Commands.DOWNLOAD_FORGE);
        mapAction(runMinecraftForgeInstallerButton, Commands.RUN_FORGE_INSTALLER);
        mapAction(downloadModsPackButton, Commands.DOWNLOAD_MODS_PACK);
    }

    private void mapAction(JButton button, String command) {
        button.setActionCommand(command);
        button.addActionListener(this);
    }

    private void setupDownloadableTables() {
        ResourceBundle bundle = ResourceBundle.getBundle("strings");
        downloadableForgeTableModel = new DownloadableTableModel(new String[]{
                bundle.getString("col.version"),
                bundle.getString("col.filename"),
                bundle.getString("col.url")});
        downloadableForgeTable.setModel(downloadableForgeTableModel);
        downloadableModTableModel = new DownloadableTableModel(new String[]{
                bundle.getString("col.name"),
                bundle.getString("col.version"),
                bundle.getString("col.forge.version"),
                bundle.getString("col.filename"),
                bundle.getString("col.url")});
        downloadableModTable.setModel(downloadableModTableModel);
    }

    @Override
    public void setDownloadableForges(List<IDownloadableRow> forges) {
        downloadableForgeTableModel.setData(forges);
    }

    @Override
    public List<IDownloadableRow> getSelectedDownloadableForges() {
        List<IDownloadableRow> list = new ArrayList<IDownloadableRow>();
        int[] rows = downloadableForgeTable.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            list.add(downloadableForgeTableModel.getRowAt(i));
        }
        return list;
    }

    @Override
    public void setDownloadableMods(List<IDownloadableRow> mods) {
        downloadableModTableModel.setData(mods);
    }

    @Override
    public List<IDownloadableRow> getSelectedDownloadableMods() {
        List<IDownloadableRow> list = new ArrayList<IDownloadableRow>();
        int[] rows = downloadableModTable.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            list.add(downloadableModTableModel.getRowAt(rows[i]));
        }
        return list;
    }

    @Override
    public void updateModList(String mcDataFolder) {
        mcDataFolderTextField.setText(mcDataFolder);
        modsTableModel.setMcDataFolder(mcDataFolder);
        modsTable.updateUI();
    }

    @Override
    public List<ModsTableRow> getSelectedMods() {
        List<ModsTableRow> list = new ArrayList<ModsTableRow>();
        int[] rows = modsTable.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            list.add(getModAt(rows[i]));
        }
        return list;
    }

    public String getMcDataFolder() {
        return mcDataFolderTextField.getText();
    }

    public void setMcDataFolder(String path) {
        mcDataFolderTextField.setText(path);
    }

    public String getMcExecutable() {
        return mcExecTextField.getText();
    }

    public void setMcExecutable(String path) {
        mcExecTextField.setText(path);
    }

    public void updateProgress(int progress) {
        progressBar2.setValue(progress);
    }

    public void updateMessage(String msg) {
        messageLabel2.setText(msg);
    }

    public List<ModsTableRow> getModifiedMods() {
        return modsTableModel.getModified();
    }

    @Override
    public void selectAllMods() {
        modsTable.selectAll();
    }

    @Override
    public void unselectAllMods() {
        modsTable.clearSelection();
    }

    @Override
    public void checkAllMods() {
        for (int i = 0; i < modsTableModel.getRowCount(); i++) {
            modsTableModel.setValueAt(true, i, 1);
        }
    }

    @Override
    public void uncheckAllMods() {
        for (int i = 0; i < modsTableModel.getRowCount(); i++) {
            modsTableModel.setValueAt(false, i, 1);
        }
    }

    @Override
    public void removeMod(ModsTableRow row) {
    }

    @Override
    public void deleteSelectedMods() {
        modsTableModel.removeMods(modsTable.getSelectedRows());
    }

    @Override
    public void removeModAt(int rowIndex) {
        modsTable.remove(rowIndex);
    }

    @Override
    public ModsTableRow getModAt(int row) {
        return modsTableModel.getRow(row);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1 = new JTabbedPane();
        contentPane.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        tabbedPane1.addTab(ResourceBundle.getBundle("strings").getString("tab.launcher"), panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 8, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        installAllButton = new JButton();
        this.$$$loadButtonText$$$(installAllButton, ResourceBundle.getBundle("strings").getString("button.install.all"));
        panel2.add(installAllButton, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uninstallAllButton = new JButton();
        this.$$$loadButtonText$$$(uninstallAllButton, ResourceBundle.getBundle("strings").getString("button.uninstall.all"));
        panel2.add(uninstallAllButton, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectAllButton = new JButton();
        this.$$$loadButtonText$$$(selectAllButton, ResourceBundle.getBundle("strings").getString("table.select.all"));
        panel2.add(selectAllButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unselectAllButton = new JButton();
        this.$$$loadButtonText$$$(unselectAllButton, ResourceBundle.getBundle("strings").getString("table.unselect.all"));
        panel2.add(unselectAllButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteSelectedButton = new JButton();
        this.$$$loadButtonText$$$(deleteSelectedButton, ResourceBundle.getBundle("strings").getString("button.delete.selected"));
        panel2.add(deleteSelectedButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshButton = new JButton();
        this.$$$loadButtonText$$$(refreshButton, ResourceBundle.getBundle("strings").getString("button.refresh"));
        panel2.add(refreshButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modsTable = new JTable();
        scrollPane1.setViewportView(modsTable);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        launchMinecraftWithForgeButton = new JButton();
        this.$$$loadButtonText$$$(launchMinecraftWithForgeButton, ResourceBundle.getBundle("strings").getString("launch.minecraft"));
        panel3.add(launchMinecraftWithForgeButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 50), null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        openModsFolderButton = new JButton();
        this.$$$loadButtonText$$$(openModsFolderButton, ResourceBundle.getBundle("strings").getString("button.open.enabled.mods.folder"));
        panel3.add(openModsFolderButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectedLabel = new JLabel();
        this.$$$loadLabelText$$$(selectedLabel, ResourceBundle.getBundle("strings").getString("label.enabled.mods"));
        panel3.add(selectedLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openDisabledModsFolderButton = new JButton();
        this.$$$loadButtonText$$$(openDisabledModsFolderButton, ResourceBundle.getBundle("strings").getString("button.open.disabled.mods.folder"));
        panel3.add(openDisabledModsFolderButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        tabbedPane1.addTab(ResourceBundle.getBundle("strings").getString("tab.settings"), panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("strings").getString("mc.data.folder"));
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mcDataFolderTextField = new JTextField();
        panel5.add(mcDataFolderTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        changeMcDataFolderButton = new JButton();
        this.$$$loadButtonText$$$(changeMcDataFolderButton, ResourceBundle.getBundle("strings").getString("button.change"));
        panel5.add(changeMcDataFolderButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        this.$$$loadLabelText$$$(label2, ResourceBundle.getBundle("strings").getString("mc.executable"));
        panel5.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mcExecTextField = new JTextField();
        panel5.add(mcExecTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        changeMcExecButton = new JButton();
        this.$$$loadButtonText$$$(changeMcExecButton, ResourceBundle.getBundle("strings").getString("button.change"));
        panel5.add(changeMcExecButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        downloadMinecraftForgeInstallerButton = new JButton();
        this.$$$loadButtonText$$$(downloadMinecraftForgeInstallerButton, ResourceBundle.getBundle("strings").getString("download.forge"));
        panel5.add(downloadMinecraftForgeInstallerButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        downloadModsPackButton = new JButton();
        this.$$$loadButtonText$$$(downloadModsPackButton, ResourceBundle.getBundle("strings").getString("button.download.selected.mods"));
        panel5.add(downloadModsPackButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        downloadableModTable = new JTable();
        scrollPane2.setViewportView(downloadableModTable);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel5.add(scrollPane3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        downloadableForgeTable = new JTable();
        scrollPane3.setViewportView(downloadableForgeTable);
        runMinecraftForgeInstallerButton = new JButton();
        this.$$$loadButtonText$$$(runMinecraftForgeInstallerButton, ResourceBundle.getBundle("strings").getString("run.forge.installer"));
        panel5.add(runMinecraftForgeInstallerButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel4.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        messageLabel2 = new JLabel();
        messageLabel2.setText("Message");
        panel6.add(messageLabel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        progressBar2 = new JProgressBar();
        panel6.add(progressBar2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
