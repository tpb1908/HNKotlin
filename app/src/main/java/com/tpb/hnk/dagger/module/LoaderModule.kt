package com.tpb.hnk.dagger.module

import com.tpb.hnk.data.database.IdDao
import com.tpb.hnk.data.database.ItemDao
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.loaders.ItemLoader
import com.tpb.hnk.data.loaders.PageLoader
import com.tpb.hnk.data.loaders.UserLoader
import com.tpb.hnk.data.models.HNIdList
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.data.services.UserService
import com.tpb.hnk.util.ConnectivityListener
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by theo on 11/07/17.
 */
@Module(includes = arrayOf(NetModule::class, DBModule::class))
class LoaderModule {
    
    @Provides
    @Singleton
    fun providePageLoader(connectivityListener: ConnectivityListener,
                          idService: IdService,
                          idDao: IdDao,
                          idPersistor: Persistor<HNIdList>): PageLoader {
        return PageLoader(connectivityListener, idService, idDao, idPersistor)
    }

    @Provides
    @Singleton
    fun provideItemLoader(connectivityListener: ConnectivityListener,
                          itemService: ItemService,
                          itemDao: ItemDao,
                          itemPersistor: Persistor<HNItem>): ItemLoader {
        return ItemLoader(connectivityListener, itemService, itemDao, itemPersistor)
    }

    @Provides
    @Singleton
    fun provideUserLoader(connectivityListener: ConnectivityListener, userService: UserService): UserLoader {
        return UserLoader(connectivityListener, userService)
    }


}