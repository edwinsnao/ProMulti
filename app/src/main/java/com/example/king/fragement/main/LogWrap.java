package com.example.king.fragement.main;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Kings on 2016/3/17.
 */
public class LogWrap {
    public static final boolean RELEASE = false;
    public static final String TAG = "MyAppTag";

    public static void e(final Object obj, final Throwable cause) {
        if (!RELEASE) {
            Log.e(TAG, String.valueOf(obj));
            Log.e(TAG, convertThrowableStackToString(cause));
        }
    }

    public static void e(final Object obj) {
        if (!RELEASE) {
            Log.e(TAG, String.valueOf(obj));
        }
    }

    public static void w(final Object obj, final Throwable cause) {
        if (!RELEASE) {
            Log.w(TAG, String.valueOf(obj));
            Log.w(TAG, convertThrowableStackToString(cause));
        }
    }

    public static void w(final Object obj) {
        if (!RELEASE) {
            Log.w(TAG, String.valueOf(obj));
        }
    }

    public static void d(final Object obj) {
        if (!RELEASE) {
            Log.d(TAG, String.valueOf(obj));
        }
    }

    public static void v(final Object obj) {
        if (!RELEASE) {
            Log.v(TAG, String.valueOf(obj));
        }
    }

    public static void i(final Object obj) {
        if (!RELEASE) {
            Log.i(TAG, String.valueOf(obj));
        }
    }

    public static String convertThrowableStackToString(final Throwable thr) {
        StringWriter b = new StringWriter();
        thr.printStackTrace(new PrintWriter(b));
        return b.toString();
    }

}
