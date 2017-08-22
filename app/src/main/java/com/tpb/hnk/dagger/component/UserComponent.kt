package com.tpb.hnk.dagger.component

import com.tpb.hnk.dagger.module.AppModule
import com.tpb.hnk.dagger.module.DBModule
import com.tpb.hnk.dagger.module.LoaderModule
import com.tpb.hnk.dagger.module.NetModule
import com.tpb.hnk.views.UserActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by theo on 21/08/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class, DBModule::class, LoaderModule::class))
interface UserComponent {

    fun inject(activity: UserActivity)

}