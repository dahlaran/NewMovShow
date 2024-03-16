package com.dahlaran.newmovshow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dahlaran.newmovshow.domain.model.Media

/**
 * AppDatabase is the room database for the app
 */
@Database(entities = [Media::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}