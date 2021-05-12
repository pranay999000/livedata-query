package com.example.wednesday.api

import android.os.Build
import com.example.wednesday.BuildConfig
import com.example.wednesday.api.Links.Companion.itunes_url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {

        Retrofit.Builder().baseUrl(itunes_url).addConverterFactory(GsonConverterFactory.create()).client(getLogInterceptor()).build()
    }

    val api: ITunesApi by lazy {
        retrofit.create(ITunesApi::class.java)
    }

    private fun getLogInterceptor(): OkHttpClient {
        val clintBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            clintBuilder.addInterceptor(interceptor)
        }
        return clintBuilder.build()
    }
}