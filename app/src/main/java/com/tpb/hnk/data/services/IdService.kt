package com.tpb.hnk.data.services

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by theo on 08/07/17.
 */
interface IdService {

    @GET("newstories.json")
    fun listNewIds(): Single<List<Long>>

    @GET("topstories.json")
    fun listTopIds(): Single<List<Long>>

    @GET("beststories.json")
    fun listBestIds(): Single<List<Long>>

    @GET("askstories.json")
    fun listAskIds(): Single<List<Long>>

    @GET("showstories.json")
    fun listShowIds(): Single<List<Long>>

    @GET("jobstories.json")
    fun listJobIds(): Single<List<Long>>

    @GET("maxitem.json")
    fun getTopId(): Single<Long>

}