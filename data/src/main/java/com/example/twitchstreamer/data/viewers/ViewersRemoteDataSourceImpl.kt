package com.example.twitchstreamer.data.viewers

import com.example.twitchstreamer.data.remote.api.BaseApi
import com.example.twitchstreamer.domain.viewers.ViewersRemoteDataSource
import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * An implementation of [ViewersRemoteDataSource] to provide remote viewers information.
 *
 * @property baseApi A [BaseApi] instance.
 */
internal class ViewersRemoteDataSourceImpl @Inject constructor(
    private val baseApi: BaseApi
) : ViewersRemoteDataSource {

    override suspend fun getViewers(amount: Int): List<ViewerModel> = withContext(Dispatchers.IO) {
        return@withContext baseApi.getViewers(amount = amount).results
            ?.map { it.name.toDomain() }
            .orEmpty()
    }
}