package com.example.actualtravellerkiviprojectui;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class App extends Application {
    private static WeakReference<Context> mContext;

    public static Context getContext() {
        return mContext.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<>(this);
    }
}
