package com.subakstudio.mclauncher.util

import com.subakstudio.mclauncher.Constants
import groovy.util.logging.Slf4j

/**
 * Created by yeoupooh on 12/30/15.
 */
@Slf4j
class McLauncherRepository {
    static def init() {
        FileUtils.mkdirs(Constants.MC_LAUNCHER_REPO_FOLDER)
        FileUtils.mkdirs(Constants.MC_LAUNCHER_REPO_FORGES_FOLDER)
    }
}
