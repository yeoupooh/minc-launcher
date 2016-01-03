package com.subakstudio.mclauncher.cmd

/**
 * Created by Thomas on 1/3/2016.
 */
class SwingFormCommandDispatcher extends CommandDispatcher {
    def swing
    def form

    def SwingFormCommandDispatcher(swing, form) {
        this.swing = swing
        this.form = form
    }

    @Override
    boolean execute(name) {
        def cmd = cmds[name]
        if (cmd != null && cmd instanceof ICommand) {
            if (cmd instanceof SwingFormCommand) {
                cmd.swing = swing
                cmd.form = form
            }
            return cmd.execute()
        }

        return false
    }
}
