package com.subakstudio.mclauncher.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.subakstudio.mclauncher.Commands;
import com.subakstudio.mclauncher.model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by yeoupooh on 1/3/16.
 */
public class McLauncherSimple extends BaseMcLauncherFrame {
    private final ModsTableModel modsTableModel;
    private JTabbedPane tabbedPane1;
    private JPanel contentPane;
    private JRadioButton installedRadioButton;
    private JRadioButton availableRadioButton;
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
    private DownloadableTableModel downloadableForgeTableModel;
    private DownloadableTableModel downloadableModTableModel;

    @Override
    public JPanel getRootContentPane() {
        return contentPane;
    }

    public McLauncherSimple() {
        setupUI();
        refreshButton.setActionCommand(Commands.REFRESH_DOWNLOADED_MODS);
        refreshButton.addActionListener(this);
        launchMinecraftWithForgeButton.setActionCommand(Commands.LAUNCH_MINECRAFT);
        launchMinecraftWithForgeButton.addActionListener(this);

        modsTableModel = new ModsTableModel();
        modsTable.setModel(modsTableModel);
//        modsTable.setCellEditor(new ModsTableCellEditor(new JTextField()));
//        modsTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor());
        modsTable.setDefaultRenderer(Boolean.class, new CheckBoxCellRenderer(false));
        modsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                selectedLabel.setText(
                        String.format("%s: %s",
                                ResourceBundle.getBundle("strings").getString("table.selected"),
                                modsTableModel.getSelected().size()
                        )
                );
            }
        });
        modsTableModel.addTableModelListener(this);

        setupDownloadableTables();

        changeMcDataFolderButton.setActionCommand(Commands.CHANGE_MC_DATA_FOLDER);
        changeMcDataFolderButton.addActionListener(this);

        changeMcExecButton.setActionCommand(Commands.CHANGE_MC_EXECUTABLE);
        changeMcExecButton.addActionListener(this);

        openModsFolderButton.setActionCommand(Commands.OPEN_INSTALLED_MODS_FOLDER);
        openModsFolderButton.addActionListener(this);

        openDisabledModsFolderButton.setActionCommand(Commands.OPEN_DISABLED_MODS_FOLDER);
        openDisabledModsFolderButton.addActionListener(this);

        downloadMinecraftForgeInstallerButton.setActionCommand(Commands.DOWNLOAD_FORGE);
        downloadMinecraftForgeInstallerButton.addActionListener(this);

        runMinecraftForgeInstallerButton.setActionCommand(Commands.RUN_FORGE_INSTALLER);
        runMinecraftForgeInstallerButton.addActionListener(this);

        downloadModsPackButton.setActionCommand(Commands.DOWNLOAD_MODS_PACK);
        downloadModsPackButton.addActionListener(this);

        selectAllButton.setActionCommand(Commands.SELECT_ALL_MODS);
        selectAllButton.addActionListener(this);

        unselectAllButton.setActionCommand(Commands.UNSELECT_ALL_MODS);
        unselectAllButton.addActionListener(this);

        refreshButton.setActionCommand(Commands.REFRESH_MOD_LIST);
        refreshButton.addActionListener(this);
    }

    private void setupDownloadableTables() {
        downloadableForgeTableModel = new DownloadableTableModel(new String[]{"Version", "Url"});
        downloadableForgeTable.setModel(downloadableForgeTableModel);
        downloadableModTableModel = new DownloadableTableModel(new String[]{"Name", "Version", "Required Forge Version", "Url"});
        downloadableModTable.setModel(downloadableModTableModel);
    }

    @Override
    public void setDownloadableForges(List<IDownloadableRow> forges) {
        downloadableForgeTableModel.setData(forges);
    }

    @Override
    public void setDownloadableMods(List<IDownloadableRow> mods) {
        downloadableModTableModel.setData(mods);
    }

    @Override
    public void updateModList(String mcDataFolder) {
        mcDataFolderTextField.setText(mcDataFolder);
        modsTableModel.setMcDataFolder(mcDataFolder);
        modsTable.updateUI();
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
        panel2.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        installedRadioButton = new JRadioButton();
        installedRadioButton.setText("Installed");
        panel2.add(installedRadioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        availableRadioButton = new JRadioButton();
        availableRadioButton.setText("Available");
        panel2.add(availableRadioButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectAllButton = new JButton();
        this.$$$loadButtonText$$$(selectAllButton, ResourceBundle.getBundle("strings").getString("table.select.all"));
        panel2.add(selectAllButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unselectAllButton = new JButton();
        this.$$$loadButtonText$$$(unselectAllButton, ResourceBundle.getBundle("strings").getString("table.unselect.all"));
        panel2.add(unselectAllButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modsTable = new JTable();
        scrollPane1.setViewportView(modsTable);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        launchMinecraftWithForgeButton = new JButton();
        this.$$$loadButtonText$$$(launchMinecraftWithForgeButton, ResourceBundle.getBundle("strings").getString("launch.minecraft"));
        panel3.add(launchMinecraftWithForgeButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 50), null, null, 0, false));
        refreshButton = new JButton();
        this.$$$loadButtonText$$$(refreshButton, ResourceBundle.getBundle("strings").getString("refresh"));
        panel3.add(refreshButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        openModsFolderButton = new JButton();
        this.$$$loadButtonText$$$(openModsFolderButton, ResourceBundle.getBundle("strings").getString("open.mods.folder"));
        panel3.add(openModsFolderButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectedLabel = new JLabel();
        selectedLabel.setText("Selected: 0");
        panel3.add(selectedLabel, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openDisabledModsFolderButton = new JButton();
        this.$$$loadButtonText$$$(openDisabledModsFolderButton, ResourceBundle.getBundle("strings").getString("open.disabled.mod.folder"));
        panel3.add(openDisabledModsFolderButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        runMinecraftForgeInstallerButton = new JButton();
        this.$$$loadButtonText$$$(runMinecraftForgeInstallerButton, ResourceBundle.getBundle("strings").getString("run.forge.installer"));
        panel5.add(runMinecraftForgeInstallerButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        downloadModsPackButton = new JButton();
        this.$$$loadButtonText$$$(downloadModsPackButton, ResourceBundle.getBundle("strings").getString("download.mods.pack"));
        panel5.add(downloadModsPackButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        downloadableModTable = new JTable();
        scrollPane2.setViewportView(downloadableModTable);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel5.add(scrollPane3, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        downloadableForgeTable = new JTable();
        scrollPane3.setViewportView(downloadableForgeTable);
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        messageLabel2 = new JLabel();
        messageLabel2.setText("Message");
        panel6.add(messageLabel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        progressBar2 = new JProgressBar();
        panel6.add(progressBar2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(installedRadioButton);
        buttonGroup.add(availableRadioButton);
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
