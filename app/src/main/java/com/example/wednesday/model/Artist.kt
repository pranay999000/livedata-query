package com.example.wednesday.model

data class Artist (
    val trackId: Long,
    val artistName: String,
    val collectionName: String,
    val trackName: String,
    val artworkUrl100: String,
    val trackTimeMillis: Long
)