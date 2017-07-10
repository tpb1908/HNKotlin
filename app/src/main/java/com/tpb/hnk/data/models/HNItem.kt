package com.tpb.hnk.data.models

/**
 * Created by theo on 08/07/17.
 */
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.res.Resources
import android.support.annotation.StringRes
import com.google.gson.annotations.SerializedName
import com.tpb.hnk.R
import io.mironov.smuggler.AutoParcelable
import java.lang.Exception
import java.net.URL

/**
 * Created by theo on 08/07/17.
 */
@Entity(tableName = "hnitem") data class HNItem(
        @SerializedName("id") @PrimaryKey var id: Long,
        @SerializedName("deleted") var deleted: Boolean,
        @SerializedName("type") var type: ItemType,
        @SerializedName("by") var by: String,
        @SerializedName("time") @ColumnInfo(name = "time") var time: Long,
        @SerializedName("text") var text: String?,
        @SerializedName("dead") var dead: Boolean,
        @SerializedName("parent") var parent: Long,
        @SerializedName("poll") var poll: Long,
        @SerializedName("kids") var kids: List<Long>?,
        @SerializedName("url") var url: String?,
        @SerializedName(value = "score", alternate = arrayOf("points")) var score: Long,
        @SerializedName("title") var title: String,
        @SerializedName("parts") var parts: List<Long>?,
        @SerializedName("descendants") var descendants: Int
) : AutoParcelable {

    fun domain(res: Resources): String? {
        if (type == ItemType.STORY) {
            try {
                return URL(url).host
            } catch (e: Exception) {
                return res.getString(R.string.type_story)
            }
        } else {
            return res.getString(type.id)
        }
    }

    override fun equals(o: Any?): Boolean {
        return o is HNItem && o.id == id && o.deleted == deleted && o.kids?.size == kids?.size && o.parts?.size == parts?.size
    }

    override fun hashCode(): Int {
        var result = 31
        result = 31 * result + id.hashCode()
        result = 31 * result + if (deleted) 1 else 0
        result = 31 * result + (kids?.size ?: 0)
        return 31 * result + (parts?.size ?: 0)
    }
}

enum class ItemType(@StringRes val id: Int) {
    @SerializedName("story") STORY(R.string.type_story),
    @SerializedName("job") JOB(R.string.type_job),
    @SerializedName("comment") COMMENT(R.string.type_comment),
    @SerializedName("poll") POLL(R.string.type_poll),
    @SerializedName("pollopt") POLLOPT(R.string.type_pollopt)
}