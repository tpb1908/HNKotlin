package com.tpb.hnk.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.tpb.hnk.data.models.HNItem

/**
 * Created by theo on 10/07/17.
 */
@Database(entities = arrayOf(HNItem::class), version = 1)
@TypeConverters(ItemTypeConverters::class)
abstract class Database : RoomDatabase() {

    abstract fun itemDao(): ItemDao

}