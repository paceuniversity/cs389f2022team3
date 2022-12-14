package com.team3.indexify.network.core

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.team3.indexify.network.models.SensorDataModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://droid.bluecolab.cc/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getSensorData] method
 */

interface ColabApiService {
    /**
     * Returns a [List] of [SensorDataModel] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "android" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("sensordata/{measurement}")
    suspend fun getSensorData(@Path("measurement") measurement : String): SensorDataModel
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */

object ColabApi {
    val retrofitService: ColabApiService by lazy { retrofit.create(ColabApiService::class.java) }
}