package com.tpb.hnk.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by theo on 09/07/17.
 */
data class MercuryPage(
        @SerializedName("title") var title: String,
        @SerializedName("content") var content: String,
        @SerializedName("date_published") var datePublished: String,
        @SerializedName("lead_image_url") var leadImageUrl: String,
        @SerializedName("dek") var dek: String,
        @SerializedName("url") var url: String,
        @SerializedName("domain") var domain: String,
        @SerializedName("excerpt") var excerpt: String,
        @SerializedName("word_count") var wordCount: Int,
        @SerializedName("direction") var direction: String,
        @SerializedName("total_pages") var totalPages: Int,
        @SerializedName("rendered_pages") var renderedPages: Int,
        @SerializedName("next_page_url") var nextPageUrl: String

)