package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.domain.model.Media


/**
 * It is used to update the UI when the MediaViewModel is updated
 *
 * @param medias The list of medias to display
 * @param isLoading True if the data is loading, false otherwise
 * @param searchQuery Searching title
 */
data class MediaListState(
    val medias: List<Media> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "")