package com.tpb.hnk.data.services

import com.tpb.hnk.data.models.HNUser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by theo on 08/07/17.
 */
interface UserService {

    @GET("/user/{id}.json")
    fun getUser(@Path("id") id: Long): Observable<HNUser>

}