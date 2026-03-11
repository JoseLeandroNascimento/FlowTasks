package com.joseleandro.flowtask.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskPreferencesDataStore(
    private val context: Context
) {

    val tagFilter: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.TAG_ID_FILTER]
        }

    suspend fun setTagFilter(tagId: Int?) {
        context.dataStore.edit { preferences ->
            if (tagId == null) {
                preferences.remove(PreferencesKeys.TAG_ID_FILTER)
            } else {
                preferences[PreferencesKeys.TAG_ID_FILTER] = tagId
            }
        }
    }
}