package com.lh.cachelibrary.utils;

import android.util.Log;

/**
 * Created by home on 2017/1/10.
 * 日志记录类
 */

public class LogUtils {
    private static final String TAG = "RxCache";

    private static LogUtils INSTANCE;

    private LogUtils() {
    }

    public static LogUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogUtils();
        }
        return INSTANCE;
    }

    public void i(String msg) {
        Log.i(TAG, msg);
    }

    public void w(String msg) {
        Log.w(TAG, msg);
    }

    public void e(String msg) {
        Log.e(TAG, msg);
    }
}
