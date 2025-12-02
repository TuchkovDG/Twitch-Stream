package com.example.twitchstreamer.data.viewers

import com.example.twitchstreamer.data.local.entity.ViewerEntity
import com.example.twitchstreamer.data.remote.model.ViewersResponse
import com.example.twitchstreamer.domain.viewers.model.ViewerModel

fun ViewersResponse.ResultResponse.NameResponse?.toDomain(): ViewerModel =
    ViewerModel(
        title = this?.title.orEmpty(),
        first = this?.first.orEmpty(),
        last = this?.last.orEmpty()
    )

fun ViewerModel.toEntity(): ViewerEntity =
    ViewerEntity(
        title = title,
        first = first,
        last = last
    )

fun ViewerEntity.toDomain(): ViewerModel =
    ViewerModel(
        title = title,
        first = first,
        last = last
    )