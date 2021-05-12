package com.example.wednesday.repository

import com.example.wednesday.api.RetrofitInstance
import com.example.wednesday.model.Artist
import com.example.wednesday.model.SearchResultModel
import retrofit2.Response

class Repository {
    suspend fun getArtistSongs(term: String): Response<SearchResultModel> {
        return RetrofitInstance.api.getArtistSongs(term)
    }
}