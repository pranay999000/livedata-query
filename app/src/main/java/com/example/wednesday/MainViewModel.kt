package com.example.wednesday

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wednesday.model.SearchResultModel
import com.example.wednesday.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    var artistSongs: MutableLiveData<Response<SearchResultModel>> = MutableLiveData()

    fun getArtistSongs(term: String) {
        viewModelScope.launch {
            val response: Response<SearchResultModel> = repository.getArtistSongs(term)
            artistSongs.value = response
        }
    }
}