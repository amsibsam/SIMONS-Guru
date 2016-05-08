package com.monitoringsiswa.monitoringguru.module;


import android.content.Context;


import com.monitoringsiswa.monitoringguru.network.MonitoringService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ExternalModule {
    @Provides
    @Singleton
    MonitoringService provideMonitoringService(Context context) {
        return new MonitoringService(context);
    }
}
