package com.example.twitchstreamer.domain.viewers

import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import kotlinx.coroutines.flow.Flow

/**
 * An local data source interface that provides viewers information.
 */
interface ViewersLocalDataSource {

    /**
     * Fetch viewers information from local source.
     */
    fun getViewers(): Flow<List<ViewerModel>>

    /**
     * Save viewers information to local source.
     */
    suspend fun saveViewers(viewers: List<ViewerModel>)
}