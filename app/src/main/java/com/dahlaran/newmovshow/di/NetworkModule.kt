package com.dahlaran.newmovshow.di

import com.dahlaran.newmovshow.data.remote.RemoteDataSource
import com.dahlaran.newmovshow.data.remote.RemoteDataSourceImpl
import com.dahlaran.newmovshow.data.remote.TMDbApiService
import com.dahlaran.newmovshow.data.remote.TVMazeApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TVMazeRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TMDbRetrofit

/**
 * Module for providing network related dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TVMAZE_API_URL = "https://api.tvmaze.com/"
    private const val TMDB_API_URL = "https://api.themoviedb.org/3/"

    /**
     * Provide Gson
     */
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    /**
     * Provide OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)

        return client.build()
    }

    /**
     * Provide Retrofit instance for TMDb API
     *
     * @param gson Gson
     * @param client OkHttpClient
     * @return Retrofit instance configured for TMDb API
     */
    @Provides
    @TMDbRetrofit
    fun provideTMDbRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(TMDB_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * Provide Retrofit instance for TVMaze API
     *
     * @param gson Gson
     * @param client OkHttpClient
     * @return Retrofit instance configured for TVMaze API
     */
    @Provides
    @TVMazeRetrofit
    fun provideTVMazeRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(TVMAZE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * Provide TVMazeApiServices
     *
     * @param retrofit Retrofit instance
     */
    @Provides
    @Singleton
    fun provideTVMazeApiService(@TVMazeRetrofit retrofit: Retrofit): TVMazeApiService {
        return retrofit.create(TVMazeApiService::class.java)
    }

    /**
     * Provide TMDbApiServices
     *
     * @param retrofit Retrofit instance
     */
    @Provides
    @Singleton
    fun provideTMDbApiService(@TMDbRetrofit retrofit: Retrofit): TMDbApiService {
        return retrofit.create(TMDbApiService::class.java)
    }

    /**
     * Provide RemoteDataSource
     *
     * @param tvMazeApiService TVMazeApiService
     * @param tmDbApiService TMDbApiService
     */
    @Provides
    @Singleton
    fun provideRemoteDataSource(
        tvMazeApiService: TVMazeApiService,
        tmDbApiService: TMDbApiService
    ): RemoteDataSource {
        return RemoteDataSourceImpl(tvMazeApiService, tmDbApiService)
    }
}