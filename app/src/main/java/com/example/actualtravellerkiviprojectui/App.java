package com.example.actualtravellerkiviprojectui;

import android.app.Application;

import com.example.actualtravellerkiviprojectui.api.mock.Utils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);  // Initialize here
    }
}
