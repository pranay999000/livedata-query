package com.example.wednesday.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.wednesday.R
import com.example.wednesday.model.Artist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.songs_item.view.*
import java.util.concurrent.TimeUnit

class SongsAdapter(private var context: Context?, private var list: ArrayList<Artist>?) : BaseAdapter() {


    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(position: Int): Artist? {
        return list?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val song = this.list?.get(position)

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.songs_item, null)

        val milliSeconds: Long = song!!.trackTimeMillis
        val minutes = milliSeconds / 1000 / 60
        val seconds = milliSeconds / 1000 % 60

        Picasso.get().load(song.artworkUrl100).into(view.song_imageView)
        view.artist_name.text = song.artistName
        view.title_text.text = song.trackName
        view.run_time.text =  String.format("%2d:%d", minutes, seconds)
//        view.run_time.text = "hello"

        return view
    }
}