package com.example.twitchstreamer.domain.config

import kotlinx.coroutines.flow.Flow

/**
 * An repository interface that provides user config stream key.
 */
interface ConfigRepository {

    /**
     * Fetch user stream key.
     */
    suspend fun getStreamKey(): Flow<String>

    /**
     * Save an user stream key.
     *
     * @param key An user stream key value.
     */
    suspend fun saveStreamKey(key: String)
}