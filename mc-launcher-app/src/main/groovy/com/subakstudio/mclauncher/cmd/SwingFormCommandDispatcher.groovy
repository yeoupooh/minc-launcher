package com.subakstudio.mclauncher.cmd

import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class SwingFormCommandDispatcher extends CommandDispatcher {
    def settings
    def swing
    def form

    def SwingFormCommandDispatcher(settings, swing, form) {
        this.settings = settings
        this.swing = swing
        this.form = form
    }

    boolean doExecuteCommand(cmd) {
        if (cmd instanceof ICommand) {
            if (cmd instanceof SwingFormCommand) {
                cmd.settings = settings
                cmd.swing = swing
                cmd.form = form
            }
            return cmd.execute()
        }

        return false
    }

    @Override
    boolean execute(name) {
        def cmd = cmds[name]
        log.debug("cmd=[$cmd]")
        if (cmd != null) {
            if (cmd.metaClass.respondsTo('each')) {
                def ret = true
                cmd.each { c ->
                    ret = doExecuteCommand(c)
                }
                return ret
            } else if (cmd instanceof ICommand) {
                doExecuteCommand(cmd)
            }
        }

        return false
    }
}
