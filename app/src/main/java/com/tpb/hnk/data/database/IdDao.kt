package com.tpb.hnk.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tpb.hnk.data.models.HNIdList
import io.reactivex.Flowable

/**
 * Created by theo on 11/07/17.
 */
@Dao interface IdDao {

    @Query("SELECT * FROM id_list")
    fun getAllIdLists(): Flowable<List<HNIdList>>

    @Query("SELECT * FROM id_list ORDER BY time LIMIT 1")
    fun getLastIdList(): Flowable<List<HNIdList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(item: HNIdList)

}