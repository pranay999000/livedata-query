package com.example.wednesday.repository

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wednesday.Adapter.SongsAdapter
import com.example.wednesday.Database.Songs
import com.example.wednesday.MainActivity
import com.example.wednesday.model.Artist
import com.example.wednesday.model.SearchResultModel
import com.example.wednesday.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(private val repository: Repository, var context: Context, activity: Activity): ViewModel() {
    lateinit var artistSongs: LiveData<List<Songs>>
    private var listen: Listen = activity as Listen

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

                listen.searchInDatabase("model")

            }
        }
    }

    fun getArtistFromRoom(name: String): ArrayList<Songs> {
        val responseList = repository.getAllArtists()
        val foundList: ArrayList<Songs> = ArrayList()
        Log.d("name", responseList.toString())
        for (artist in responseList) {
            Log.d("name", "for loop")
            if (artist.artistName?.toLowerCase()?.contains(name.toLowerCase())!!) {
                Log.d("name", name)
                foundList.add(artist)
            }
        }

        Log.d("found", foundList.toString())
        return foundList
    }

    interface Listen {
        fun searchInDatabase(from: String)
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