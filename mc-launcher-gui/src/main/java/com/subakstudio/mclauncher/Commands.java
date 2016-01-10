package com.subakstudio.mclauncher;

/**
 * Created by yeoupooh on 1/1/16.
 */
public class Commands {
    // Launcher tab
    public static final String SELECT_ALL_MODS = "select.all.mods";
    public static final String UNSELECT_ALL_MODS = "unselect.all.mods";
    public static final String DELETE_SELECTED_MODS = "delete.selected.mods";

    public static final String ENABLE_SELECTED_MODS = "enable.selected.mods";
    public static final String DISABLE_SELECTED_MODS = "disable.selected.mods";
    public static final String ENABLE_ALL_MODS = "enable.all.mods";
    public static final String DISABLE_ALL_MODS = "disable.all.mods";

    public static final String REFRESH_MOD_LIST = "refresh.mod.list";
    public static final String OPEN_INSTALLED_MODS_FOLDER = "open.installed.mods.folder";
    public static final String OPEN_DISABLED_MODS_FOLDER = "open.disabled.mods.folder";
    public static final String LAUNCH_MINECRAFT = "launch.minecraft";

    // Mods Downloader tab
    public static final String UPDATE_MODS_URL = "update.mods.url";
    public static final String REFRESH_DOWNLOADABLE_MODS = "refresh.downloadable.mods";
    public static final String DOWNLOAD_SELECTED_MODS = "download.selected.mods";

    // Settings tab
    public static final String CHANGE_MC_DATA_FOLDER = "change.mc.data.folder";
    public static final String CHANGE_MC_EXECUTABLE = "change.mc.executable";
    public static final String REFRESH_DOWNLOADABLE_FORGES = "refresh.downloadable.forges";
    public static final String DOWNLOAD_FORGE = "download.forge";
    public static final String RUN_FORGE_INSTALLER = "run.forge.installer";
}
