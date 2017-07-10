package com.tpb.hnk.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.tpb.hnk.data.models.HNItem
import io.reactivex.Flowable

/**
 * Created by theo on 10/07/17.
 */
@Dao interface ItemDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flowable<List<HNItem>>

    @Insert
    fun insertItem(item: HNItem): Long


}