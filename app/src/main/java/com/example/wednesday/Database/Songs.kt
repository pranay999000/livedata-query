package com.example.wednesday.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "songs_table")
data class Songs(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val artistName: String?,
    val trackName: String?,
    val collectionName: String?,
    val image: String?,
    val runTime: Long?
)