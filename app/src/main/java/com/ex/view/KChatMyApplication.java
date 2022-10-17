package com.ex.view;

import android.app.Application;
import android.content.Context;

import com.ex.view.util.DataUtils;

public class KChatMyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        DataUtils.destroy();
    }
}
