package com.tpb.hnk.dagger.module

import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.tpb.hnk.data.database.Database
import com.tpb.hnk.data.database.ItemDao
import com.tpb.hnk.data.database.Persistor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by theo on 10/07/17.
 */
@Module class DBModule(val context: Context, val dbName: String, val migrations: Array<Migration>) {

    @Provides
    @Singleton
    fun provideDatabase(): Database {
        return Room.databaseBuilder(context, Database::class.java, dbName)
                .addMigrations(*migrations)
                .build()
    }


    @Provides
    @Singleton
    fun provideItemDao(database: Database): ItemDao {
        return database.itemDao()
    }

    @Provides
    @Singleton
    fun providePersistor(database: Database): Persistor {
        return Persistor(database)
    }

}