package com.subakstudio.mclauncher.cmd

import com.subakstudio.mclauncher.model.Settings
import groovy.swing.SwingBuilder

/**
 * Created by Thomas on 1/3/2016.
 */
abstract class SwingFormCommand implements ICommand {
    Settings settings
    SwingBuilder swing
    def form
}
