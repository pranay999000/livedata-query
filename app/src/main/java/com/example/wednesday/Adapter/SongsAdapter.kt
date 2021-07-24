package com.example.wednesday.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wednesday.Database.Songs
import com.example.wednesday.databinding.SongsItemBinding
import com.example.wednesday.model.Artist
import com.squareup.picasso.Picasso

class SongsAdapter(private var context: Context?, private var list: ArrayList<Songs>):
    RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

        inner class ViewHolder (val binding: SongsItemBinding): RecyclerView.ViewHolder(binding.root)

    fun fillData(list: ArrayList<Songs>) {
        this.list.clear()
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SongsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder) {
            with (list[position]) {
                binding.titleText.text = trackName
                binding.artistName.text = artistName

                val milliSeconds: Long? = trackTimeMillis
                val minutes = milliSeconds!! / 1000 / 60
                val seconds = milliSeconds / 1000 % 60

                binding.runTime.text = String.format("%2d:%d", minutes, seconds)
                Picasso.get().load(artworkUrl100).into(binding.songImageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}