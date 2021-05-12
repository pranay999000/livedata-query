package com.example.wednesday.api

import com.example.wednesday.model.SearchResultModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    @GET("search")
    suspend fun getArtistSongs (
        @Query("term") term: String
    ): Response<SearchResultModel>
}