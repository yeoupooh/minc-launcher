package com.subakstudio.mclauncher.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * Created by yeoupooh on 1/3/16.
 */
public abstract class BaseMcLauncherFrame extends JFrame implements ActionListener {

    private ActionListener actionListener;

    public abstract Container getRootContentPane();

    public void setupUI() {
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
    public void actionPerformed(ActionEvent e) {
        if (actionListener != null) {
            actionListener.actionPerformed(e);
        }
    }
}
