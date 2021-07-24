package com.example.wednesday.Database

import androidx.lifecycle.LiveData

class SongsRepository(private val songsDao: SongsDAO) {
    val realAllData: LiveData<List<Songs>> = songsDao.readAllSongs()

    suspend fun addSongs(songs: List<Songs>) {
        songsDao.addSongs(songs)
    }
}