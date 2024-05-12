package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.domain.model.Media

/**
 * It is used to update the UI when the MainState is updated
 *
 * @param medias The list of medias to display
 * @param mediaPage The page of the medias
 * @param isLoading True if the data is loading, false otherwise
 * @param searchQuery Searching title
 */
data class MainState(
    val medias: List<Media> = emptyList(),
    val mediaPage: Int = 0,
    val isLoading: Boolean = false,
    val searchQuery: String = "")