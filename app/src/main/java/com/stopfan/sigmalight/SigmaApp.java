package com.stopfan.sigmalight;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Denys on 11/3/2015.
 */
public class SigmaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
        else
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    // Nothing to show
                }
            });
    }
}
