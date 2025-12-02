package com.example.twitchstreamer.data.viewers

import com.example.twitchstreamer.data.local.dao.ViewerDao
import com.example.twitchstreamer.domain.viewers.ViewersLocalDataSource
import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * An implementation of [ViewersLocalDataSource] to provide local viewers information.
 *
 * @property viewerDao A [ViewerDao] instance.
 */
internal class ViewersLocalDataSourceImpl @Inject constructor(
    private val viewerDao: ViewerDao
) : ViewersLocalDataSource {

    override fun getViewers(): Flow<List<ViewerModel>> =
        viewerDao.observeViewers().map { entityList ->
            entityList.map { it.toDomain() }
        }

    override suspend fun saveViewers(viewers: List<ViewerModel>) = withContext(Dispatchers.IO) {
        viewerDao.replaceAll(viewers.map { it.toEntity() })
    }
}