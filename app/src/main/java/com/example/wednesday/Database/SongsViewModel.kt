package com.example.wednesday.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongsViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<Songs>>
    private val repository: SongsRepository

    init {
        val songsDAO = SongsDatabase.getDatabase(application).sonsDao()
        repository = SongsRepository(songsDAO)
        readAllData = repository.realAllData
    }

    fun addSongs(songs: Songs) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSongs(songs)
        }
    }
}