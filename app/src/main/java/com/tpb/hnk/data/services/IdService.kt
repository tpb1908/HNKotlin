package com.tpb.hnk.data.services

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by theo on 08/07/17.
 */
interface IdService {

    @GET("newstories.json")
    fun listNewIds(): Observable<List<Long>>

    @GET("topstories.json")
    fun listTopIds(): Observable<List<Long>>

    @GET("beststories.json")
    fun listBestIds(): Observable<List<Long>>

    @GET("askstories.json")
    fun listAskIds(): Observable<List<Long>>

    @GET("showstories.json")
    fun listShowIds(): Observable<List<Long>>

    @GET("jobstories.json")
    fun listJobIds(): Observable<List<Long>>

    @GET("maxitem.json")
    fun getTopId(): Observable<Long>

}