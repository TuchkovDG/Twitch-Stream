package com.example.twitchstreamer.data.config

import com.example.twitchstreamer.domain.config.ConfigLocalDataSource
import com.example.twitchstreamer.domain.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * An implementation of [ConfigRepository] to provide access to user stream config.
 *
 * @property dataSource A [ConfigLocalDataSource] instance.
 */
internal class ConfigRepositoryImpl @Inject constructor(
    private val dataSource: ConfigLocalDataSource
) : ConfigRepository {

    override suspend fun getStreamKey(): Flow<String> = dataSource.getStreamKey()

    override suspend fun saveStreamKey(key: String) = dataSource.saveStreamKey(key = key)
}
