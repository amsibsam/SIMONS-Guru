package com.monitoringsiswa.monitoringguru.module;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InternalModule {
    @Provides
    @Singleton
    AccountInfoStore provideAccountInfoStore(Context context){
        return new AccountInfoStore(context);
    }
}
