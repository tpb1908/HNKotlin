package com.tpb.hnk

import android.app.Application
import com.tpb.hnk.dagger.component.DaggerMainComponent
import com.tpb.hnk.dagger.component.MainComponent
import com.tpb.hnk.dagger.module.AppModule
import com.tpb.hnk.dagger.module.DBModule
import com.tpb.hnk.dagger.module.LoaderModule
import com.tpb.hnk.dagger.module.NetModule

/**
 * Created by theo on 08/07/17.
 */
class App : Application() {

    lateinit var netComponent: MainComponent
        private set


    override fun onCreate() {
        super.onCreate()
        netComponent = DaggerMainComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(this, "https://hacker-news.firebaseio.com/v0/", "https://mercury.postlight.com"))
                .dBModule(DBModule(this, packageName, arrayOf()))
                .loaderModule(LoaderModule())
                .build()
    }
}