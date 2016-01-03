package com.subakstudio.mclauncher.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * Created by yeoupooh on 1/3/16.
 */
public abstract class BaseMcLauncherFrame extends JFrame implements IMcLauncherForm {

    private ActionListener actionListener;
    private TableModelListener tableModelListener;
    private Logger log;

    public abstract Container getRootContentPane();

    public abstract void updateModList(String mcDataFolder);

    public void setupUI() {
        log = LoggerFactory.getLogger(BaseMcLauncherFrame.class);
        setContentPane(getRootContentPane());
        setTitle(ResourceBundle.getBundle("strings").getString("app.name"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourceBundle.getBundle("mclauncher").getString("app.icon"))));
        setVisible(true);
        setSize(800, 600);
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void setTableModelListener(TableModelListener tableModelListener) {
        this.tableModelListener = tableModelListener;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        log.debug(String.format("tableChanged: e=[%s]", e));
        if (tableModelListener != null) {
            tableModelListener.tableChanged(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.debug(String.format("actionPerformed: e=[%s]", e));
        if (actionListener != null) {
            actionListener.actionPerformed(e);
        }
    }
}
