package com.example.twitchstreamer.data.config

import com.example.twitchstreamer.domain.config.ConfigRepository
import com.example.twitchstreamer.domain.config.GetStreamKeyUseCase
import com.example.twitchstreamer.domain.config.SaveStreamKeyUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * An implementation of [GetStreamKeyUseCase] that provides the stream key via the [ConfigRepository].
 *
 * @property configRepository A [ConfigRepository] instance.
 */
internal class GetStreamKeyUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetStreamKeyUseCase {

    override suspend fun invoke(): Flow<String> = configRepository.getStreamKey()
}

/**
 * An implementation of [SaveStreamKeyUseCase] that provides ability to save an stream key via the [ConfigRepository].
 *
 * @property configRepository A [ConfigRepository] instance.
 */
internal class SaveStreamKeyUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : SaveStreamKeyUseCase {

    override suspend fun invoke(key: String) = configRepository.saveStreamKey(key = key)
}