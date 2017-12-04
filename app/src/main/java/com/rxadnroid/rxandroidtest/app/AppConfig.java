package com.rxadnroid.rxandroidtest.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.rxadnroid.rxandroidtest.app.utils.FileUtil;

/**
 * Created by 子非鱼 on 2017/5/12.
 */
public class AppConfig {
    private final static String APP_CONFIG = "config";
    public static final String KEY_FRITST_START = "KEY_FRIST_START";
    public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
    public static final String KEY_DOUBLE_CLICK_EXIT = "KEY_DOUBLE_CLICK_EXIT";

    public static final int IMAGE_MAX_WIDTH = 512;

    public static String DEFAULT_SAVE_ROOT_PATH;

    // 默认存放图片的路径
    public static String DEFAULT_SAVE_IMAGE_PATH;

    // 默认存放文件下载的路径
    public static String DEFAULT_SAVE_FILE_PATH;

    public static String DEFAULT_SAVE_CACHE_PATH;

    public static String DEFAULT_CORP_IMAGE_PATH;

    public static String DEFAULT_SAVE_VEDIO_PATH;

    private Context mContext;

    public static String APP_KEY = "1";
    public static String SIGN = "1";

    private static AppConfig appConfig;


    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;

            if(!FileUtil.checkExternalSDExists()) {
                DEFAULT_SAVE_ROOT_PATH = context.getExternalFilesDir("/lixiangxiang/").getAbsolutePath()+"/";
                DEFAULT_SAVE_IMAGE_PATH = context.getExternalFilesDir("/lixiangxiang/image/").getAbsolutePath()+"/";
                DEFAULT_SAVE_FILE_PATH = context.getExternalFilesDir("/lixiangxiang/download/").getAbsolutePath()+"/";
                DEFAULT_SAVE_CACHE_PATH = context.getExternalFilesDir("/lixiangxiang/cache/").getAbsolutePath()+"/";
                DEFAULT_CORP_IMAGE_PATH = context.getExternalFilesDir("/lixiangxiang/corp/").getAbsolutePath()+"/";
                DEFAULT_SAVE_VEDIO_PATH = context.getExternalFilesDir("/lixiangxiang/dedio/").getAbsolutePath()+"/";
            }else{
                DEFAULT_SAVE_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/lixiangxiang/";
                DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/lixiangxiang/image/";
                DEFAULT_SAVE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/lixiangxiang/download/";
                DEFAULT_SAVE_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/lixiangxiang/cache/";
                DEFAULT_CORP_IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/lixiangxiang/corp/";
                DEFAULT_SAVE_VEDIO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/lixiangxiang/vedio/";
            }
        }
        return appConfig;
    }
}
