package com.Gangasagar.namahatta_katha;

import android.app.Application;

public class OneSignal extends Application {

    private static final String ONESIGNAL_APP_ID = "8ffb7325-6e87-47e6-ad6d-4b5c83215fda";

    @Override
    public void onCreate() {
        super.onCreate();

        com.onesignal.OneSignal.initWithContext(this);
        com.onesignal.OneSignal.setAppId(ONESIGNAL_APP_ID);

    }
}
