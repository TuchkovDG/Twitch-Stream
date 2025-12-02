package com.example.twitchstreamer.data.viewers

import com.example.twitchstreamer.domain.viewers.GetLocalViewersUseCase
import com.example.twitchstreamer.domain.viewers.GetRemoteViewersUseCase
import com.example.twitchstreamer.domain.viewers.ViewersRepository
import com.example.twitchstreamer.domain.viewers.model.ViewerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * An implementation of [GetLocalViewersUseCase] that provide a local list of viewers via the [ViewersRepository].
 *
 * @property repository A [ViewersRepository] instance.
 */
internal class GetLocalViewersUseCaseCaseImpl @Inject constructor(
    private val repository: ViewersRepository
) : GetLocalViewersUseCase {

    override fun invoke(): Flow<List<ViewerModel>> = repository.getLocalViewers()
}

private const val AMOUNT_OF_USERS = 20

/**
 * An implementation of [GetRemoteViewersUseCase] that provide a remote list of viewers via the [ViewersRepository].
 *
 * @property repository A [ViewersRepository] instance.
 */
internal class GetRemoteViewersUseCaseImpl @Inject constructor(
    private val repository: ViewersRepository
) : GetRemoteViewersUseCase {

    override suspend fun invoke(): List<ViewerModel> = repository.getRemoteViewers(AMOUNT_OF_USERS)
}