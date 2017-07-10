package com.tpb.hnk.dagger.component

import com.tpb.hnk.dagger.module.AppModule
import com.tpb.hnk.dagger.module.NetModule
import com.tpb.hnk.views.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by theo on 08/07/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface MainComponent {

    fun inject(activity: MainActivity)

}