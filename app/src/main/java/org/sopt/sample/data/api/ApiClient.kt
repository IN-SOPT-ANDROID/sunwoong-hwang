package org.sopt.sample.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.sample.BuildConfig.REGRES_BASE_URL
import org.sopt.sample.BuildConfig.SOPT_BASE_URL
import retrofit2.Retrofit

object ApiClient {
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    val soptRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOPT_BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    val regresRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(REGRES_BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}