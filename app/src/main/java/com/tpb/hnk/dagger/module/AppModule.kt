package com.tpb.hnk.dagger.module

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by theo on 08/07/17.
 */
@Module class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources {
        return application.resources
    }
}