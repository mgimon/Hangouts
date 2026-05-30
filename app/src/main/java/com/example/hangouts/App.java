package com.example.hangouts;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import java.util.Date;

public class App extends Application {

    private long lastBackgroundTime = 0;

    DefaultLifecycleObserver AppLifecycleObserver = new DefaultLifecycleObserver() {
        @Override
        public void onStart(@NonNull LifecycleOwner owner) {
            DefaultLifecycleObserver.super.onStart(owner);
            long time = getLastBackgroundTime();

            if (time != 0) {
                Toast.makeText(App.this, "Last background: " + new Date(time),
                        Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onStop(@NonNull LifecycleOwner owner) {
            DefaultLifecycleObserver.super.onStop(owner);
            setLastBackgroundTime(System.currentTimeMillis());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(AppLifecycleObserver);
    }

    public void setLastBackgroundTime(long time) {
        lastBackgroundTime = time;
    }

    public long getLastBackgroundTime() {
        return lastBackgroundTime;
    }
}
