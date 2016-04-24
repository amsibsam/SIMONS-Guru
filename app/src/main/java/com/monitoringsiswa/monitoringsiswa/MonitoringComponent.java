package com.monitoringsiswa.monitoringsiswa;

import android.app.Application;


import com.monitoringsiswa.monitoringsiswa.module.ApplicationModule;
import com.monitoringsiswa.monitoringsiswa.module.ExternalModule;
import com.monitoringsiswa.monitoringsiswa.module.InternalModule;
import com.monitoringsiswa.monitoringsiswa.ui.activity.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by root on 09/04/16.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        InternalModule.class,
        ExternalModule.class
})
public interface MonitoringComponent {
        void inject(LoginActivity loginActivity);

        final class Initializer {
                public static MonitoringComponent init(Application application) {
                        return DaggerMonitoringComponent.builder()
                                .applicationModule(new ApplicationModule(application))
                                .build();
                }
        }
}
