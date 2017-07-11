package com.tpb.hnk.data.models

import android.support.annotation.StringRes
import com.google.gson.annotations.SerializedName
import com.tpb.hnk.R

/**
 * Created by theo on 11/07/17.
 */
enum class ItemType(@StringRes val id: Int) {
    @SerializedName("story") STORY(R.string.type_story),
    @SerializedName("job") JOB(R.string.type_job),
    @SerializedName("comment") COMMENT(R.string.type_comment),
    @SerializedName("poll") POLL(R.string.type_poll),
    @SerializedName("pollopt") POLLOPT(R.string.type_pollopt)
}