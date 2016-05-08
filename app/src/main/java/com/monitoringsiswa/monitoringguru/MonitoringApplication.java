package com.monitoringsiswa.monitoringguru;

import android.app.Application;

/**
 * Created by root on 09/04/16.
 */
public class MonitoringApplication extends Application {
    private static MonitoringApplication instance;
    private MonitoringComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        component = MonitoringComponent.Initializer.init(this);


    }

    public static MonitoringApplication getInstance() {
        return instance;
    }

    public static MonitoringComponent getComponent() {
        return instance.component;
    }
}
