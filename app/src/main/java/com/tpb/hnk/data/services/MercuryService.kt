package com.tpb.hnk.data.services

import com.tpb.hnk.BuildConfig
import com.tpb.hnk.data.models.MercuryPage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by theo on 09/07/17.
 */
interface MercuryService {

    @GET("parser?url={url}")
    fun getParsedPage(@Path("url") url: String): Observable<MercuryPage>


}