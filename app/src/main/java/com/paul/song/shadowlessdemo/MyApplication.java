package com.paul.song.shadowlessdemo;

import android.app.Application;

import com.paul.song.analytics.sdk.ShadowlessDataAPI;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initShadowlessDataAPI(this);
    }

    /**
     * 初始化埋点 SDK
     *
     * @param application Application
     */
    private void initShadowlessDataAPI(Application application) {
        ShadowlessDataAPI.init(application);
        ShadowlessDataAPI.setDebugMode(true);
    }
}
