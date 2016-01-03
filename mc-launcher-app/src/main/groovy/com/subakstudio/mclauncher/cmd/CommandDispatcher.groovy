package com.subakstudio.mclauncher.cmd

import groovy.util.logging.Slf4j

/**
 * Created by Thomas on 1/3/2016.
 */
@Slf4j
class CommandDispatcher {
    def cmds = [:]

    def putCommand(name, cmd) {
        cmds[name] = cmd
    }

    def boolean execute(name) {
        if (cmds[name] != null && cmds[name] instanceof ICommand) {
            return cmds[name].execute()
        }

        return false
    }
}
