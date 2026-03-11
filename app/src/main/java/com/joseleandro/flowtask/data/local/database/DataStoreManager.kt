package com.joseleandro.flowtask.data.local.database

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(
    name = "settings"
)

object DataStoreKeys {

    val TAG_ID_FILTER = intPreferencesKey("tag_id_filter")

}

class DataStoreManager(
    private val context: Context
) {

    val tagFilter: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[DataStoreKeys.TAG_ID_FILTER]
        }

    suspend fun setTagFilter(tagId: Int?) {
        context.dataStore.edit { preferences ->
            if (tagId == null) {
                preferences.remove(DataStoreKeys.TAG_ID_FILTER)
            } else {
                preferences[DataStoreKeys.TAG_ID_FILTER] = tagId
            }
        }
    }
}