package com.monitoringsiswa.monitoringsiswa.module;


import android.content.Context;


import com.monitoringsiswa.monitoringsiswa.network.MonitoringService;

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
