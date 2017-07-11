package com.tpb.hnk.dagger.module

import android.content.Context
import com.tpb.hnk.data.Loader
import com.tpb.hnk.data.database.ItemDao
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.data.services.ItemService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by theo on 11/07/17.
 */
@Module(includes = arrayOf(NetModule::class, DBModule::class))
class LoaderModule(val context: Context) {
    
    @Provides
    @Singleton
    fun provideLoader(itemService: ItemService, dao: ItemDao, persistor: Persistor<HNItem>): Loader {
        return Loader(context, itemService, dao, persistor)
    }
    
}