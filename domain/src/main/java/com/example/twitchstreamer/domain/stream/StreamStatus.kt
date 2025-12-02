package com.example.twitchstreamer.domain.stream


enum class StreamStatus {
    OFFLINE,
    CONNECTING,
    ONLINE,
    RECONNECTING,
    ERROR
}