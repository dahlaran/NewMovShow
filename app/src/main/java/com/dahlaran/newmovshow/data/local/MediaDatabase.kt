package com.dahlaran.newmovshow.data.local

import com.dahlaran.newmovshow.domain.model.Media

/**
 * MediaDatabase is an interface that can be used to get medias
 */
interface MediaDatabase {
    /**
     * Get a list of medias
     */
    fun getMedias(): List<Media>

    /**
     * Save a list of medias
     *
     * @param medias the list of medias to save
     */
    fun saveMedias(medias: List<Media>)

    /**
     * Get a media by id
     *
     * @param id the id to get
     */
    fun getMediaById(id: String): Media?

    /**
     * Get favorite medias
     *
     * @return a list of favorite medias
     */
    fun getFavoriteMedias(): List<Media>

    /**
     * Add a media to favorite
     *
     * @param mediaId the media id to add to favorite
     * @return the media added to favorite
     */
    fun addFavoriteMedia(mediaId: String): Media?

    /**
     * Remove a media from favorite
     *
     * @param mediaId the media id to remove from favorite
     * @return the media removed from favorite
     */
    fun removeFavoriteMedia(mediaId: String): Media?
}