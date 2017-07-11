package com.tpb.hnk.data.models

import android.arch.persistence.room.Entity
import com.tpb.hnk.data.services.HNPage
import io.mironov.smuggler.AutoParcelable

/**
 * Created by theo on 11/07/17.
 */
@Entity(tableName = "id_list", primaryKeys = arrayOf("type", "time"))
data class HNIdList(
        var time: Long,
        var type: HNPage,
        var ids: List<Long>
) : AutoParcelable