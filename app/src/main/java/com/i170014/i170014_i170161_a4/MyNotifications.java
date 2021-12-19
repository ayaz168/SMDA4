package com.i170014.i170014_i170161_a4;

import android.app.Application;

import com.onesignal.OneSignal;

public class MyNotifications extends Application {
private static final String ONESIGNAL_APP_ID = "622b7574-2ea5-4385-b3ca-d8ad828bf1ff";

@Override
public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        }
}