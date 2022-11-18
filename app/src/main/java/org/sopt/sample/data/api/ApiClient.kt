package org.sopt.sample.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiClient {
    private const val SOPT_BASE_URL = "http://3.39.169.52:3000/"
    private const val REGRES_BASE_URL = "https://reqres.in/"
    private var soptRetrofit: Retrofit? = null
    private var regresRetrofit: Retrofit? = null

    @OptIn(ExperimentalSerializationApi::class)
    fun getRetrofitForSopt(): Retrofit {
        if (soptRetrofit == null) {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            soptRetrofit = Retrofit.Builder()
                .baseUrl(SOPT_BASE_URL)
                .client(client)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
        }
        return soptRetrofit!!
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getRetrofitForRegres(): Retrofit {
        if (regresRetrofit == null) {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            regresRetrofit = Retrofit.Builder()
                .baseUrl(REGRES_BASE_URL)
                .client(client)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
        }
        return regresRetrofit!!
    }
}