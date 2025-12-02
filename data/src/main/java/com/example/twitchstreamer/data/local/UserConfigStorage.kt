package com.example.twitchstreamer.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.twitchstreamer.data.local.di.UserConfigDataStore
import com.example.twitchstreamer.data.local.extensions.getString
import com.example.twitchstreamer.data.local.extensions.putString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

internal const val USER_CONFIG_STORAGE = "user_config_storage"

private const val STREAM_KEY_ID = "stream_key_id"

/**
 * A class that provides storage for the user config.
 *
 * @property dataStore A [Preferences] typed [DataStore] instance.
 */
internal class UserConfigStorage @Inject constructor(
    @UserConfigDataStore private val dataStore: DataStore<Preferences>,
) {

    /**
     * A flow of the stream key.
     *
     * @return The value of the stream key
     */
    fun getStreamKey(): Flow<String> {
        return dataStore.getString(
            key = STREAM_KEY_ID,
            defaultValue = ""
        ).distinctUntilChanged()
    }

    /**
     * Save an user stream key.
     *
     * @param key An user stream key value.
     */
    suspend fun saveStreamKey(key: String) {
        dataStore.putString(
            key = STREAM_KEY_ID,
            value = key
        )
    }
}