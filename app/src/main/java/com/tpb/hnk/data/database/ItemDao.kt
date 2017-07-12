package com.tpb.hnk.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tpb.hnk.data.models.HNItem
import io.reactivex.Flowable

/**
 * Created by theo on 10/07/17.
 */
@Dao interface ItemDao {

    @Query("SELECT * FROM hnitem")
    fun getAllItems(): Flowable<List<HNItem>>

    @Query("SELECT * FROM hnitem WHERE id = :id LIMIT 1")
    fun getById(id: Long): Flowable<List<HNItem>>

    @Query("SELECT * FROM hnitem WHERE id IN (:ids)")
    fun getAllById(ids: List<Long>): Flowable<List<HNItem>>

    @Query("DELETE FROM hnitem WHERE time < :time")
    fun deleteOlderThan(time: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: HNItem)


}