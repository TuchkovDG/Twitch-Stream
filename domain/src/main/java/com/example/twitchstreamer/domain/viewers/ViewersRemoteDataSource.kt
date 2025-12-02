package com.example.twitchstreamer.domain.viewers

import com.example.twitchstreamer.domain.viewers.model.ViewerModel

/**
 * An remote data source interface that provides viewers information.
 */
interface ViewersRemoteDataSource {

    /**
     * Fetch viewers information by [ViewerModel] from remote source.
     */
    suspend fun getViewers(amount: Int): List<ViewerModel>
}