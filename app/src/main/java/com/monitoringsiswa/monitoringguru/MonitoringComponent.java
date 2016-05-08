package com.monitoringsiswa.monitoringguru;

import android.app.Application;


import com.monitoringsiswa.monitoringguru.module.ApplicationModule;
import com.monitoringsiswa.monitoringguru.module.ExternalModule;
import com.monitoringsiswa.monitoringguru.module.InternalModule;
import com.monitoringsiswa.monitoringguru.ui.activity.LoginActivity;
import com.monitoringsiswa.monitoringguru.ui.fragment.HomeFragment;
import com.monitoringsiswa.monitoringguru.ui.fragment.InputPelanggaranFragment;

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
        void inject(HomeFragment homeFragment);
        void inject(InputPelanggaranFragment inputPelanggaranFragment);

        final class Initializer {
                public static MonitoringComponent init(Application application) {
                        return DaggerMonitoringComponent.builder()
                                .applicationModule(new ApplicationModule(application))
                                .build();
                }
        }
}
