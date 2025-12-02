package com.example.twitchstreamer.data.config

import com.example.twitchstreamer.data.local.UserConfigStorage
import com.example.twitchstreamer.domain.config.ConfigLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * An implementation of [ConfigLocalDataSource] to provide access to user stream config.
 *
 * @property userConfigStorage A [UserConfigStorage] instance.
 */
internal class ConfigLocalDataSourceImpl @Inject constructor(
    private val userConfigStorage: UserConfigStorage,
) : ConfigLocalDataSource {

    override suspend fun getStreamKey(): Flow<String> = withContext(Dispatchers.IO) {
        return@withContext userConfigStorage.getStreamKey()
    }

    override suspend fun saveStreamKey(key: String) = withContext(Dispatchers.IO) {
        userConfigStorage.saveStreamKey(key = key)
    }
}