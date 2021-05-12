package com.example.wednesday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wednesday.Adapter.SongsAdapter
import com.example.wednesday.Database.Songs
import com.example.wednesday.Database.SongsViewModel
import com.example.wednesday.databinding.ActivityMainBinding
import com.example.wednesday.model.Artist
import com.example.wednesday.repository.MainViewModelFactory
import com.example.wednesday.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var songsViewModel: SongsViewModel

    private var songsList = ArrayList<Artist>()
    private val adapter by lazy { SongsAdapter(this, songsList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        songsViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(SongsViewModel::class.java)

        binding.searchButton.setOnClickListener {
            search()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
            }
            true
        }
    }

    private fun search() {
        progressBarLoading.visibility=View.VISIBLE
        val name: String = binding.searchEditText.text.toString()
        viewModel.getArtistSongs(name)

        viewModel.artistSongs.observe(this, Observer { response ->
            songsList.clear()
            if (response.isSuccessful) {
                Toast.makeText(this, "Hate", Toast.LENGTH_SHORT).show()
                val tempSongsList = (response.body()!!.getArtistList() as ArrayList<Artist>?)!!
                songsList.addAll(tempSongsList)

                for (i in tempSongsList) {
                    addToDatabase(i.artistName, i.trackName, i.collectionName, i.artworkUrl100, i.trackTimeMillis)
                }

                binding.songsGrid.adapter = adapter
                adapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, response.code().toString(), Toast.LENGTH_SHORT).show()
            }
            progressBarLoading.visibility=View.GONE
        })
    }

    private fun addToDatabase (artistName: String?, trackName: String?, collectionName: String?, image: String?, runtime: Long?, id: Int = 0) {
        val song = Songs(id, artistName, trackName, collectionName, image, runtime)
        songsViewModel.addSongs(song)
//        Toast.makeText(this, song.collectionName, Toast.LENGTH_SHORT).show()
    }
}
