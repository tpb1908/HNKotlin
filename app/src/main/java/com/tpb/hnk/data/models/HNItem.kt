package com.tpb.hnk.data.models

/**
 * Created by theo on 08/07/17.
 */
import com.google.gson.annotations.SerializedName

/**
 * Created by theo on 08/07/17.
 */
data class HNItem(
        @SerializedName("id") var id: Long,
        @SerializedName("deleted") var deleted: Boolean,
        @SerializedName("type") var type: ItemType,
        @SerializedName("by") var by: String,
        @SerializedName("time") var time: Long,
        @SerializedName("text") var text: String,
        @SerializedName("dead") var dead: Boolean,
        @SerializedName("parent") var parent: Long,
        @SerializedName("poll") var poll: Long,
        @SerializedName("kids") var kids: LongArray,
        @SerializedName("score") var score: Long,
        @SerializedName("title") var title: String,
        @SerializedName("parts") var parts: LongArray,
        @SerializedName("descendants") var descendants: Long

)

enum class ItemType {
    STORY, JOB, COMMENT, POLL, POLLOPT
}