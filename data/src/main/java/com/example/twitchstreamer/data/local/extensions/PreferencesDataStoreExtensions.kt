package com.example.twitchstreamer.data.local.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Retrieve a [String] value from the storage represented as a [Flow].
 *
 * @param key The name of the entry to retrieve.
 * @param defaultValue The value to return if the entry does not exist.
 * @return A [Flow] representation of the value.
 */
fun DataStore<Preferences>.getString(key: String, defaultValue: String = ""): Flow<String> =
    data.map {
        it[stringPreferencesKey(name = key)] ?: defaultValue
    }

/**
 * Set a [String] value in storage.
 *
 * @param key The name for the entry to modify.
 * @param value The new value for the entry.
 */
suspend fun DataStore<Preferences>.putString(key: String, value: String) {
    edit {
        it[stringPreferencesKey(name = key)] = value
    }
}