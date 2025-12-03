package com.example.twitchstreamer.data.viewers

import com.example.twitchstreamer.data.remote.api.ViewersApi
import com.example.twitchstreamer.domain.viewers.ViewersRemoteDataSource
import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import javax.inject.Inject

/**
 * An implementation of [ViewersRemoteDataSource] to provide remote viewers information.
 *
 * @property viewersApi A [ViewersApi] instance.
 */
internal class ViewersRemoteDataSourceImpl @Inject constructor(
    private val viewersApi: ViewersApi
) : ViewersRemoteDataSource {

    override suspend fun getViewers(amount: Int): List<ViewerModel> {
        return viewersApi.getViewers(amount = amount).results
            ?.map { it.name.toDomain() }
            .orEmpty()
    }
}