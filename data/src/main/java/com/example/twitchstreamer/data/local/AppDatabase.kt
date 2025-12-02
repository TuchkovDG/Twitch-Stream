package com.example.twitchstreamer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.twitchstreamer.data.local.dao.ViewerDao
import com.example.twitchstreamer.data.local.entity.ViewerEntity

@Database(
    entities = [
        ViewerEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun viewerDao(): ViewerDao
}
