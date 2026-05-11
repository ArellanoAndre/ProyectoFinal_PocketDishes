package islas.abril.pocketdishes.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class UserPreferences(private val context: Context) {

    companion object {
        val NAME_KEY = stringPreferencesKey("username")
        val USER_ID_KEY = intPreferencesKey("userId")
    }

    val getName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[NAME_KEY]
        }

    val getUserId: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: -1
        }

    suspend fun saveUsername(date: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = date
        }
    }

    suspend fun saveUserId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
            }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
            }
        }
    }