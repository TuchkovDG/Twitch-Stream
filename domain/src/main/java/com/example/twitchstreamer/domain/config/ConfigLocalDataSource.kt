package com.example.twitchstreamer.domain.config

import kotlinx.coroutines.flow.Flow

/**
 * An local data source interface that provides user config stream key.
 */
interface ConfigLocalDataSource {

    /**
     * Fetch user stream key.
     */
    fun getStreamKey(): Flow<String>

    /**
     * Save an user stream key.
     *
     * @param key An user stream key value.
     */
    suspend fun saveStreamKey(key: String)
}