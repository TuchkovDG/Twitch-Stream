package com.example.twitchstreamer.data.viewers

import com.example.twitchstreamer.domain.viewers.ViewersLocalDataSource
import com.example.twitchstreamer.domain.viewers.ViewersRemoteDataSource
import com.example.twitchstreamer.domain.viewers.ViewersRepository
import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * An implementation of [ViewersRepository] to provide access to viewers information.
 *
 * @property localDataSource A [ViewersLocalDataSource] instance.
 * @property remoteDataSource A [ViewersRemoteDataSource] instance.
 */
internal class ViewersRepositoryImpl @Inject constructor(
    private val localDataSource: ViewersLocalDataSource,
    private val remoteDataSource: ViewersRemoteDataSource
) : ViewersRepository {

    override fun getLocalViewers(): Flow<List<ViewerModel>> =
        localDataSource.getViewers()

    override suspend fun getRemoteViewers(amount: Int): List<ViewerModel> {
        return remoteDataSource.getViewers(amount = amount).also {
            localDataSource.saveViewers(it)
        }
    }
}