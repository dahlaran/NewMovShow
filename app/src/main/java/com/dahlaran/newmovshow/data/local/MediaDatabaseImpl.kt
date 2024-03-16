package com.dahlaran.newmovshow.data.local

import com.dahlaran.newmovshow.domain.model.Media

/**
 * MediaDatabaseImpl is the implementation of MediaDatabase
 */
class MediaDatabaseImpl(private val mediaDao: MediaDao) : MediaDatabase {
    override fun getMedias(): List<Media> {
        return try {
            mediaDao.getMedias()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun saveMedias(medias: List<Media>) {
        try {
            mediaDao.insertMedias(medias)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getMediaById(id: String): Media? {
        try {
            return mediaDao.getMediaById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}