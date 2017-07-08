package com.tpb.hnk

import android.app.Application
import com.tpb.hnk.dagger.component.DaggerNetComponent
import com.tpb.hnk.dagger.component.NetComponent
import com.tpb.hnk.dagger.module.AppModule
import com.tpb.hnk.dagger.module.NetModule

/**
 * Created by theo on 08/07/17.
 */
class App : Application() {

    lateinit var netComponent: NetComponent
        private set

    override fun onCreate() {
        super.onCreate()

        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule("https://hacker-news.firebaseio.com/v0/"))
                .build()

    }
}