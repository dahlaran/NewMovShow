package com.dahlaran.newmovshow.di

import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
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
import javax.inject.Singleton

/**
 * Module for providing network related dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_URL = "https://api.tvmaze.com/"

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
     * Provide Retrofit
     *
     * @param gson Gson
     * @param client OkHttpClient
     */
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(API_URL)
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
    fun provideApiService(retrofit: Retrofit): TVMazeApiServices {
        return retrofit.create(TVMazeApiServices::class.java)
    }
}