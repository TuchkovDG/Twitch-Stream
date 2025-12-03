package com.example.twitchstreamer.data.config

import com.example.twitchstreamer.data.local.UserConfigStorage
import com.example.twitchstreamer.domain.config.ConfigLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * An implementation of [ConfigLocalDataSource] to provide access to user stream config.
 *
 * @property userConfigStorage A [UserConfigStorage] instance.
 */
internal class ConfigLocalDataSourceImpl @Inject constructor(
    private val userConfigStorage: UserConfigStorage,
) : ConfigLocalDataSource {

    override fun getStreamKey(): Flow<String> {
        return userConfigStorage.getStreamKey()
    }

    override suspend fun saveStreamKey(key: String) {
        userConfigStorage.saveStreamKey(key = key)
    }
}