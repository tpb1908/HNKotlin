package com.tpb.hnk.data.services

import com.tpb.hnk.data.models.MercuryPage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by theo on 09/07/17.
 */
interface MercuryService {

    @GET("parser")
    fun getParsedPage(@Query("url") url: String): Observable<MercuryPage>


}