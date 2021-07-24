package com.example.wednesday.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wednesday.Database.Songs
import com.example.wednesday.Database.SongsDAO
import com.example.wednesday.Database.SongsDatabase
import com.example.wednesday.Database.SongsViewModel
import com.example.wednesday.api.RetrofitInstance
import com.example.wednesday.model.Artist
import com.example.wednesday.model.SearchResultModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class Repository(application: Application): AndroidViewModel(application) {
    private var artists: List<Songs>
    private var songsViewModel: SongsViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(SongsViewModel::class.java)

    init {
        artists = songsViewModel.getArtist()
    }

    fun insert(artistList: List<Songs>?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (artistList != null) {
                songsViewModel.addSongs(artistList)
            }
        }
    }

    fun getAllArtists(): List<Songs> {
        return artists
    }

    suspend fun getArtistSongs(term: String): Response<SearchResultModel> {
        return RetrofitInstance.api.getArtistSongs(term)
    }
}