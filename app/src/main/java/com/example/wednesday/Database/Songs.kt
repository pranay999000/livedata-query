package com.example.wednesday.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "songs_table")
data class Songs(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val artistName: String?,
        val trackName: String?,
        val collectionName: String?,
        val artworkUrl100: String?,
        val trackTimeMillis: Long?
)