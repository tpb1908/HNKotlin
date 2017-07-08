package com.tpb.hnk.data.services

import com.tpb.hnk.data.models.HNItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by theo on 08/07/17.
 */
interface ItemService {

    @GET("item/{id}.json")
    fun getItem(@Path("id") id: Long): Observable<HNItem>

}