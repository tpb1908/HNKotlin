package com.tpb.hnk.data.models

/**
 * Created by theo on 08/07/17.
 */
import com.google.gson.annotations.SerializedName
import java.lang.Exception
import java.net.URL

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
        @SerializedName("url") var url: String,
        @SerializedName("score") var score: Long,
        @SerializedName("title") var title: String,
        @SerializedName("parts") var parts: LongArray,
        @SerializedName("descendants") var descendants: Long

) {

    fun domain(): String? {
        try {
            return URL(url).host
        } catch (e: Exception) {
            return null
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is HNItem && other.id == id && other.deleted == deleted && other.kids.size == kids.size && other.parts.size == parts.size
    }

    override fun hashCode(): Int {
        var result = 31
        result = 31 * result + id.hashCode()
        result = 31 * result + if (deleted) 1 else 0
        result = 31 * result + kids.size
        return 31 * result + parts.size
    }
}

enum class ItemType {
    STORY, JOB, COMMENT, POLL, POLLOPT
}