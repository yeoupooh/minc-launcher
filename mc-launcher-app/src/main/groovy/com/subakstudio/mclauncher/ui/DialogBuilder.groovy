package com.subakstudio.mclauncher.ui

import com.subakstudio.mclauncher.util.ResStrings
import groovy.swing.SwingBuilder

import javax.swing.JOptionPane
import java.awt.Component

/**
 * Created by yeoupooh on 1/16/16.
 */
class DialogBuilder {
    def swing
    def parent
    String msg
    String title
    int type

    DialogBuilder(swing, parent) {
        this.swing = swing
        this.parent = parent
    }

    DialogBuilder buildError(String msg) {
        this.msg = msg
        this.title = ResStrings.get("dialog.title.error")
        this.type = JOptionPane.ERROR_MESSAGE
        return this
    }

    DialogBuilder buildErrorWithResId(String resId) {
        return buildError(ResStrings.get(resId))
    }

    def show() {
        (swing as SwingBuilder).optionPane().showMessageDialog(parent as Component, msg, title, type)
    }

}
