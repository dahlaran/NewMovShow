package com.dahlaran.newmovshow.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dahlaran.newmovshow.domain.model.Media

/**
 * MediaDao is the Data Access Object for Media
 */
@Dao
interface MediaDao {

    /**
     * Get a list of medias
     */
    @Query("SELECT * FROM media")
    fun getMedias(): List<Media>

    /**
     * Get a media by id
     *
     *  @param id the id to get the media
     */
    @Query("SELECT * FROM media WHERE id = :id")
    fun getMediaById(id: String): Media?

    /**
     * Get favorite medias
     *
     * @return a list of favorite medias
     */
    @Query("SELECT * FROM media WHERE isFavorite = 1")
    fun getFavoriteMedias(): List<Media>

    /**
     * Insert a list of medias
     *
     *  @param medias the list of medias to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedias(medias: List<Media>)

    /**
     * Update the isFavorite status of a media and return the updated media
     *
     * @param id the id of the media to update
     * @param isFavorite the new favorite status
     * @return the updated media
     */
    @Query("UPDATE media SET isFavorite = :isFavorite WHERE id = :id")
    fun updateFavoriteStatus(id: String, isFavorite: Boolean): Int

    /**
     * Delete all medias
     */
    @Query("DELETE FROM media")
    fun deleteAllMedias()
}
