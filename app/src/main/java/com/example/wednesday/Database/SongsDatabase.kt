package com.example.wednesday.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Songs::class], version = 1, exportSchema = false)
abstract class SongsDatabase: RoomDatabase() {
    abstract fun sonsDao(): SongsDAO

    companion object {
        @Volatile
        private var INSTANCE: SongsDatabase? = null

        fun getDatabase (context: Context): SongsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder (
                    context.applicationContext,
                    SongsDatabase::class.java,
                    "songs_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}