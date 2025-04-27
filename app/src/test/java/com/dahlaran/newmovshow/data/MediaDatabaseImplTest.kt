package com.dahlaran.newmovshow.data

import com.dahlaran.newmovshow.data.local.MediaDao
import com.dahlaran.newmovshow.data.local.MediaDatabaseImpl
import com.dahlaran.newmovshow.domain.model.Media
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class MediaDatabaseImplTest {
    private lateinit var mediaDao: MediaDao
    private lateinit var mediaDatabase: MediaDatabaseImpl

    @Before
    fun setup() {
        mediaDao = mockk()
        mediaDatabase = MediaDatabaseImpl(mediaDao)
    }

    @Test
    fun `getMedias returns data from dao`() {
        val expectedList = listOf(createTestMedia("1"), createTestMedia("2"))

        coEvery { mediaDao.getMedias() } returns expectedList
        val result = mediaDatabase.getMedias()

        assert(result == expectedList)
    }

    @Test
    fun `getMedias handles exception`() {
        coEvery { mediaDao.getMedias() } throws RuntimeException("Database error")

        val result = mediaDatabase.getMedias()

        assert(result.isEmpty())
    }

    @Test
    fun `getMediaById returns data from dao`() {
        val mediaId = "123"
        val expectedMedia = createTestMedia(mediaId)
        coEvery { mediaDao.getMediaById(mediaId) } returns expectedMedia

        val result = mediaDatabase.getMediaById(mediaId)

        assert(result == expectedMedia)
    }

    @Test
    fun `getMediaById handles exception`() {
        val mediaId = "123"
        coEvery { mediaDao.getMediaById(mediaId) } throws RuntimeException("Database error")

        val result = mediaDatabase.getMediaById(mediaId)

        assert(result == null)
    }

    @Test
    fun `getFavoriteMedias returns data from dao`() {
        val favoriteList = listOf(createTestMedia("1", true), createTestMedia("2", true))
        coEvery { mediaDao.getFavoriteMedias() } returns favoriteList

        val result = mediaDatabase.getFavoriteMedias()

        assert(result == favoriteList)
    }

    @Test
    fun `addFavoriteMedia updates status and returns updated media`() {
        val mediaId = "123"
        val updatedMedia = createTestMedia(mediaId, true)
        coEvery { mediaDao.updateFavoriteStatus(mediaId, true) } returns 1
        coEvery { mediaDao.getMediaById(mediaId) } returns updatedMedia

        val result = mediaDatabase.addFavoriteMedia(mediaId)

        assert(result == updatedMedia)
    }

    @Test
    fun `removeFavoriteMedia updates status and returns updated media`() {
        val mediaId = "123"
        val updatedMedia = createTestMedia(mediaId, false)
        coEvery { mediaDao.updateFavoriteStatus(mediaId, false) } returns 1
        coEvery { mediaDao.getMediaById(mediaId) } returns updatedMedia

        val result = mediaDatabase.removeFavoriteMedia(mediaId)

        assert(result == updatedMedia)
    }

    private fun createTestMedia(id: String, isFavorite: Boolean = false): Media {
        return Media(
            id = id,
            genres = listOf("Drama", "Comedy"),
            image = "http://example.com/image.jpg",
            language = "English",
            title = "Test Show $id",
            officialSite = "http://example.com",
            premiered = "2021-01-01",
            rating = 8.5,
            runtime = 60,
            seasons = null,
            status = "Running",
            summary = "Test summary",
            type = "scripted",
            updated = 12345678,
            url = "http://example.com/show",
            weight = 100,
            isFavorite = isFavorite
        )
    }
}