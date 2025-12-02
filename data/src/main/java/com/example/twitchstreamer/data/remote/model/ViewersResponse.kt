package com.example.twitchstreamer.data.remote.model

import com.google.gson.annotations.SerializedName

data class ViewersResponse(
    @SerializedName("results")
    val results: List<ResultResponse>?
) {

    data class ResultResponse(
        @SerializedName("name")
        val name: NameResponse?
    ) {

        data class NameResponse(
            @SerializedName("title")
            val title: String?,
            @SerializedName("first")
            val first: String?,
            @SerializedName("last")
            val last: String?
        )
    }
}