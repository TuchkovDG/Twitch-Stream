package com.example.twitchstreamer.domain.config

import kotlinx.coroutines.flow.Flow

/**
 * An use case to provide a given users stream key.
 */
interface GetStreamKeyUseCase {

    suspend operator fun invoke(): Flow<String>
}

/**
 * An use case for saving user stream key.
 */
interface SaveStreamKeyUseCase {

    /**
     * Save an user stream key.
     *
     * @param key An user stream key value.
     */
    suspend operator fun invoke(key: String)
}