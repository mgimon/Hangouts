package com.example.hangouts;

import android.widget.Toast;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.Date;

public class AppLifecycleObserver implements DefaultLifecycleObserver {

    private final Hangouts app;

    public AppLifecycleObserver(Hangouts app) {
        this.app = app;
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        app.setLastBackgroundTime(System.currentTimeMillis());
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        long time = app.getLastBackgroundTime();

        if (time != 0) {
            Toast.makeText(app,
                    "Last background: " + new Date(time),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
