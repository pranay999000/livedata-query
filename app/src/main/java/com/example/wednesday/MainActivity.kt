package com.example.wednesday

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wednesday.Adapter.SongsAdapter
import com.example.wednesday.Database.Songs
import com.example.wednesday.databinding.ActivityMainBinding
import com.example.wednesday.repository.MainViewModel
import com.example.wednesday.repository.MainViewModelFactory
import com.example.wednesday.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var name: String

    private var songsList = ArrayList<Songs>()
    private lateinit var adapter: SongsAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        val repository = Repository(application)
        val viewModelFactory = MainViewModelFactory(repository, this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getArtistFromRoom().observe(this, { response ->
            songsList.clear()
            if (response != null && response.isNotEmpty() && ::name.isInitialized) {
                songsList.addAll(response)
                fillSongs(name)
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        adapter = SongsAdapter(this, songsList)

        binding.songsGrid.layoutManager = gridLayoutManager
        binding.songsGrid.setHasFixedSize(true)
        binding.songsGrid.adapter = adapter

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
            }
            true
        }

        binding.searchButton.setOnClickListener {
            search()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun search() {
        name = binding.searchEditText.text.toString()
        viewModel.getArtistSongs(name)
        fillSongs(name)
    }

    private fun fillSongs(from: String) {
//        Toast.makeText(this, from, Toast.LENGTH_SHORT).show()

        val foundList: ArrayList<Songs> = ArrayList()
        for (artist in songsList) {
            if (artist.artistName?.toLowerCase().equals(from.toLowerCase())) {
                foundList.add(artist)
            }
        }

        Log.d("main", songsList.toString())
        if (foundList.isNotEmpty()) {
            binding.nothingFoundTest.visibility = View.GONE
        } else {
            binding.nothingFoundTest.visibility = View.VISIBLE
        }
        adapter.fillData(foundList)
    }
}
