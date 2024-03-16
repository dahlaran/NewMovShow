package com.dahlaran.newmovshow.di

import com.dahlaran.newmovshow.domain.repository.MediaRepository
import com.dahlaran.newmovshow.data.TVMazeRepository
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for providing repository related dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provide MediaRepository
     *
     * @param tvMazeApiServices the TVMazeApiServices for remote data
     * @param mediaDatabase the MediaDatabase for local storage
     * @return the MediaRepository instance
     */
    @Singleton
    @Provides
    fun providesMediaRepository(
        tvMazeApiServices: TVMazeApiServices,
        mediaDatabase: MediaDatabase
    ): MediaRepository {
        return TVMazeRepository(tvMazeApiServices, mediaDatabase)
    }
}