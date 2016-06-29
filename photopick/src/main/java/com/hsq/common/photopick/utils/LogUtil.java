package com.hsq.common.photopick.utils;

import android.util.Log;

/**
 * Created by heshiqi on 16/5/8.
 */
public class LogUtil {
    
    private static long startTime = 0;
    private static final String TAG = "LogUtil";
    private static final int NO_LEVEL = Log.DEBUG;
    private static final int LOG_LEVEL = NO_LEVEL;
    private static final boolean isDebug = true;

    //冗余信息输出
    public static void v(String tag, String msg) {
        if (Log.VERBOSE >= LOG_LEVEL && isDebug) {
            Log.v(tag, msg);
        }
    }

    //调试信息输出
    public static void d(String tag, String msg) {
        if (Log.DEBUG >= LOG_LEVEL && isDebug) {
            Log.d(tag, msg);
        }
    }

    //提示信息输出
    public static void i(String tag, String msg) {
        if (Log.INFO >= LOG_LEVEL && isDebug) {
            Log.i(tag, msg);
        }
    }

    //警告信息输出
    public static void w(String tag, String msg) {
        if (Log.WARN >= LOG_LEVEL && isDebug) {
            Log.w(tag, msg);
        }
    }

    //错误信息输出
    public static void e(String tag, String msg) {
        if (Log.ERROR >= LOG_LEVEL && isDebug) {
            Log.e(tag, msg);
        }
    }

    /*
     * 记录方法调用的开始时间
     */
    public static void startTime() {
        startTime = System.currentTimeMillis();
        d(TAG, "start time:" + startTime);
    }

    /*
     * 记录方法调用的使用时间
     */
    public static void useTime() {
        long endTime = System.currentTimeMillis();
        d(TAG, "use time:" + (endTime - startTime));
    }
}
