package com.traday.longholder.local.preferences.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first


abstract class BasePreferences(protected val dataStore: DataStore<Preferences>) {

    protected suspend fun write(
        operation: suspend (mutablePreferences: MutablePreferences) -> Unit
    ): Unit {
        dataStore.edit { operation(it) }
        return Unit
    }

    protected suspend fun <T : Any?> read(
        operation: suspend (preferences: Preferences) -> T
    ): T = operation(dataStore.data.first())
}
