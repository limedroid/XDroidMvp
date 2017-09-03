package cn.droidlover.xdroidmvp;

import cn.droidlover.xdroidmvp.imageloader.ILoader;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * Created by wanglei on 2016/12/4.
 */

public class XDroidConf {
    // #log
    public static boolean LOG = true;
    public static String LOG_TAG = "XDroid";

    // #cache
    public static String CACHE_SP_NAME = "config";
    public static String CACHE_DISK_DIR = "cache";

    // #router
    public static int ROUTER_ANIM_ENTER = Router.RES_NONE;
    public static int ROUTER_ANIM_EXIT = Router.RES_NONE;

    // #imageloader
    public static int IL_LOADING_RES = ILoader.Options.RES_NONE;
    public static int IL_ERROR_RES = ILoader.Options.RES_NONE;

    // #dev model
    public static boolean DEV = true;

    /**
     * config log
     *
     * @param log
     * @param logTag
     */
    public static void configLog(boolean log, String logTag) {
        LOG = log;
        if (!Kits.Empty.check(logTag)) {
            LOG_TAG = logTag;
        }
    }

    /**
     * conf cache
     *
     * @param spName
     * @param diskDir
     */
    public static void configCache(String spName, String diskDir) {
        if (!Kits.Empty.check(spName)) {
            CACHE_SP_NAME = spName;
        }
        if (!Kits.Empty.check(diskDir)) {
            CACHE_DISK_DIR = diskDir;
        }
    }

    /**
     * config dev
     *
     * @param isDev
     */
    public static void devMode(boolean isDev) {
        DEV = isDev;
    }

}
