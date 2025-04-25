package com.dahlaran.newmovshow.di

import android.content.Context
import androidx.room.Room
import com.dahlaran.newmovshow.data.local.AppDatabase
import com.dahlaran.newmovshow.data.local.MediaDao
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.local.MediaDatabaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for providing database related dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provide AppDatabase
     *
     * @param applicationContext Android application context
     */
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Provide MediaDao
     *
     * @param appDatabase Room generated database
     */
    @Singleton
    @Provides
    fun provideMediaDao(appDatabase: AppDatabase) = appDatabase.mediaDao()

    /**
     * Provide MediaDatabase
     *
     * @param mediaDao Room generated DAO
     */
    @Singleton
    @Provides
    fun provideMediaDatabase(mediaDao: MediaDao): MediaDatabase {
        return MediaDatabaseImpl(mediaDao)
    }
}