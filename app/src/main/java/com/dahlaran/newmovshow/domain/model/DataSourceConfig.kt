package com.dahlaran.newmovshow.domain.model

/**
 * DataSourceConfig is a configuration class that holds the enabled data sources for the application.
 */
object DataSourceConfig {
    val enabledSources: Set<DataSource> = setOf(
        DataSource.TVMAZE,
        DataSource.TMDB
    )
    // TODO: Add a way to change the current source
    var currentSource: DataSource = DataSource.TMDB
}

/**
 * DataSource is an enum class that represents the different data sources available in the application.
 */
enum class DataSource {
    TVMAZE, TMDB
}