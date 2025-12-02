package com.example.twitchstreamer.domain.viewers

import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import kotlinx.coroutines.flow.Flow

/**
 * Interface to provide a local list of viewers.
 */
interface GetLocalViewersUseCase {

    operator fun invoke(): Flow<List<ViewerModel>>
}

/**
 * Interface to provide a remote list of viewers.
 */
interface GetRemoteViewersUseCase {

    suspend operator fun invoke(): List<ViewerModel>
}