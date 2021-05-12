package com.example.wednesday.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wednesday.Database.SongsRepository
import com.example.wednesday.Database.SongsViewModel

class SongsViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SongsViewModel(application) as T
    }
}