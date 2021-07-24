package com.example.wednesday.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wednesday.Database.Songs
import com.example.wednesday.model.SearchResultModel
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.collections.ArrayList

class MainViewModel(private val repository: Repository, var context: Context): ViewModel() {
    lateinit var artistSongs: LiveData<List<Songs>>

    @RequiresApi(Build.VERSION_CODES.M)
    fun getArtistSongs(term: String) {
        viewModelScope.launch {
            var response: Response<SearchResultModel>? = null
            if (isConnected(context)) {
                response = repository.getArtistSongs(term)
            }

            if (response != null && response.isSuccessful) {
                val artistList: ArrayList<Songs> = ArrayList()
                for (artist in response.body()?.getArtistList()!!) {
                    val songs = Songs(
                            artist.trackId,
                            artist.artistName,
                            artist.trackName,
                            artist.collectionName,
                            artist.artworkUrl100,
                            artist.trackTimeMillis
                    )
                    artistList.add(songs)
                }
                Log.d("array", artistList.toString())
                repository.insert(artistList)

            }
        }
    }

    fun getArtistFromRoom(): LiveData<List<Songs>> {
        return repository.getAllArtists()
    }

}

@RequiresApi(Build.VERSION_CODES.M)
fun isConnected(context: Context): Boolean {
    val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}