package com.tpb.hnk.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by theo on 08/07/17.
 */
@Entity data class HNUser(
        @SerializedName("id") @PrimaryKey var id: Long,
        @SerializedName("delay") var delay: Long,
        @SerializedName("created") var created: Long,
        @SerializedName("karma") var karma: Long,
        @SerializedName("about") var about: String,
        @SerializedName("submitted") var submitted: LongArray
)