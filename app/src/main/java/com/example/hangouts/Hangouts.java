package com.example.hangouts;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

public class Hangouts extends Application {

    private long lastBackgroundTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle()
                .addObserver(new AppLifecycleObserver(this));
    }

    public void setLastBackgroundTime(long time) {
        lastBackgroundTime = time;
    }

    public long getLastBackgroundTime() {
        return lastBackgroundTime;
    }
}
