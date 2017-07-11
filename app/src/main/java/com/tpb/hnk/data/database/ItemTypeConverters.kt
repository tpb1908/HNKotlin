package com.tpb.hnk.data.database

import android.arch.persistence.room.TypeConverter
import com.tpb.hnk.data.models.ItemType
import com.tpb.hnk.data.services.HNPage

/**
 * Created by theo on 10/07/17.
 */
class ItemTypeConverters {

    @TypeConverter fun longArrayToString(array: List<Long>?): String {
        return array?.joinToString(transform = { it.toString() }, separator = ",") ?: ""
    }

    @TypeConverter fun stringToLongArray(array: String?): List<Long> {
        return array?.split(",")?.map { if (it.isEmpty()) 0 else it.toLong() } ?: listOf()
    }

    @TypeConverter fun itemTypeToString(type: ItemType?): String {
        return when (type) {
            ItemType.COMMENT -> "comment"
            ItemType.JOB -> "job"
            ItemType.POLL -> "poll"
            ItemType.POLLOPT -> "pollopt"
            else -> "story"
        }
    }

    @TypeConverter fun stringToItemType(type: String?): ItemType {
        return when (type) {
            "comment" -> ItemType.COMMENT
            "job" -> ItemType.JOB
            "poll" -> ItemType.POLL
            "pollopt" -> ItemType.POLLOPT
            else -> ItemType.STORY
        }
    }

    @TypeConverter fun hnPageToString(page: HNPage): String {
        return when (page) {
            HNPage.ASK -> "ask"
            HNPage.BEST -> "best"
            HNPage.JOB -> "job"
            HNPage.NEW -> "new"
            HNPage.SHOW -> "show"
            HNPage.TOP -> "top"
        }
    }

    @TypeConverter fun stringToHNPage(page: String): HNPage {
        return when (page) {
            "ask" -> HNPage.ASK
            "best" -> HNPage.BEST
            "job" -> HNPage.JOB
            "new" -> HNPage.NEW
            "show" -> HNPage.SHOW
            else -> HNPage.TOP
        }
    }

}