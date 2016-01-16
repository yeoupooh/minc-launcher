package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.model.Settings
import com.subakstudio.mclauncher.ui.DialogBuilder
import com.subakstudio.mclauncher.util.ResStrings
import groovy.swing.SwingBuilder

import javax.swing.JOptionPane
import java.awt.Dialog

/**
 * Created by Thomas on 1/3/2016.
 */
abstract class SwingFormCommand implements ICommand {
    Settings settings
    SwingBuilder swing
    def form
    DialogBuilder dialogBuilder
}
