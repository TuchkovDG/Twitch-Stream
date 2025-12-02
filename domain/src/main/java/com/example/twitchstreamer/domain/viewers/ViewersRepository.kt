package com.example.twitchstreamer.domain.viewers

import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import kotlinx.coroutines.flow.Flow

/**
 * An repository interface that represents viewers information.
 */
interface ViewersRepository {

    /**
     * Fetch viewers information from local source.
     */
    fun getLocalViewers(): Flow<List<ViewerModel>>

    /**
     * Fetch viewers information from remote source.
     */
    suspend fun getRemoteViewers(amount: Int): List<ViewerModel>
}