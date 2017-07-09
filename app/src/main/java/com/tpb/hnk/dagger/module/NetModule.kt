package com.tpb.hnk.dagger.module

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tpb.hnk.BuildConfig
import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.data.services.MercuryService
import com.tpb.hnk.data.services.UserService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by theo on 08/07/17.
 */
@Module class NetModule(val hnBaseUrl: String, val mercuryBaseUrl: String) {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .setLenient()
                .create()
    }
    
    @Provides
    @Singleton
    @Named("MERCURY_OKHTTP")
    fun provideMercuryOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder()
                            .header("x-api-key", BuildConfig.MERCURY_API_KEY)
                            .build())
                }
                .build()
    }
    
    @Provides
    @Singleton
    @Named("HN_OKHTTP")
    fun provideHNOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    @Named("HN_RETROFIT")
    fun provideHNRetrofit(gson: Gson, @Named("HN_OKHTTP") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(okHttpClient)
                .baseUrl(hnBaseUrl)
                .build()
    }

    @Provides
    @Singleton
    @Named("MERCURY_RETROFIT")
    fun provideMercuryRetrofit(gson: Gson, @Named("MERCURY_OKHTTP") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(okHttpClient)
                .baseUrl(mercuryBaseUrl)
                .build()
    }

    @Provides
    @Singleton
    fun provideIdService(@Named("HN_RETROFIT") retrofit: Retrofit): IdService {
        return retrofit.create(IdService::class.java)
    }

    @Provides
    @Singleton
    fun provideItemService(@Named("HN_RETROFIT") retrofit: Retrofit): ItemService {
        return retrofit.create(ItemService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(@Named("HN_RETROFIT") retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideMercuryService(@Named("MERCURY_RETROFIT") retrofit: Retrofit): MercuryService {
        return retrofit.create(MercuryService::class.java)
    }

}