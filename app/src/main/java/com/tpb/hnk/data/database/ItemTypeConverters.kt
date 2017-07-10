package com.tpb.hnk.data.database

import android.arch.persistence.room.TypeConverter
import com.tpb.hnk.data.models.ItemType

/**
 * Created by theo on 10/07/17.
 */
class ItemTypeConverters {

    @TypeConverter fun longArrayToString(array: List<Long>): String {
        return array.joinToString(transform = { it.toString() }, separator = ",")
    }

    @TypeConverter fun stringToLongArray(array: String): List<Long> {
        return array.split(",").map { it.toLong() }
    }

    @TypeConverter fun itemTypeToString(type: ItemType): String {
        return when (type) {
            ItemType.COMMENT -> "comment"
            ItemType.JOB -> "job"
            ItemType.POLL -> "poll"
            ItemType.POLLOPT -> "pollopt"
            ItemType.STORY -> "story"
        }
    }

    @TypeConverter fun stringToItemType(type: String): ItemType {
        return when (type) {
            "comment" -> ItemType.COMMENT
            "job" -> ItemType.JOB
            "poll" -> ItemType.POLL
            "pollopt" -> ItemType.POLLOPT
            else -> ItemType.STORY
        }
    }

}