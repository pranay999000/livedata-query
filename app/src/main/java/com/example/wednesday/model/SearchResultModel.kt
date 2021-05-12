package com.example.wednesday.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResultModel {

    @SerializedName("resultCount")
    @Expose
    private var resultCount: Int? = null
    @SerializedName("results")
    @Expose
    private var artistList: List<Artist>? = null

    fun getResultCount(): Int? {
        return resultCount
    }

    fun getArtistList(): List<Artist>? {
        return artistList
    }



}