package com.hv.cfi.constants;

import android.util.Log;

/**
 * Created by Himanshu Kumar Singh
 */
public class Logger {
    public static void e(String tag, String message) {
        if (Constant.DEBUG)
            Log.e(tag, message);
    }

    public static void d(String tag, String message) {
        if (Constant.DEBUG)
            Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        if (Constant.DEBUG)
            Log.i(tag, message);
    }

    public static void v(String tag, String message) {
        if (Constant.DEBUG)
            Log.v(tag, message);
    }

    public static void w(String tag, String message) {
        if (Constant.DEBUG)
            Log.w(tag, message);
    }
}
