package com.example.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.data.model.UserRole
import com.example.i18n.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object KaamPrefsStore {
    @Volatile
    private var INSTANCE: DataStore<Preferences>? = null

    fun getInstance(context: Context): DataStore<Preferences> {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: PreferenceDataStoreFactory.create(
                produceFile = { context.applicationContext.preferencesDataStoreFile("kaam_prefs") }
            ).also { INSTANCE = it }
        }
    }

    internal fun resetForTests() {
        synchronized(this) {
            INSTANCE = null
        }
    }
}

data class AppUserPreferences(
    val language: AppLanguage = AppLanguage.ENGLISH,
    val activeRole: UserRole = UserRole.CUSTOMER,
    val activeCustomerId: String = "cust_local_1",
    val activeWorkerId: String = "wrk_local_1",
    val showSamplePreviewData: Boolean = true,
    val darkTheme: Boolean = false,
    val favoriteWorkerIds: List<String> = emptyList()
)

class UserPreferencesRepository(private val context: Context) {
    private val dataStore: DataStore<Preferences>
        get() = KaamPrefsStore.getInstance(context)

    private object Keys {
        val LANGUAGE = stringPreferencesKey("language_code")
        val ACTIVE_ROLE = stringPreferencesKey("active_role")
        val ACTIVE_CUSTOMER_ID = stringPreferencesKey("active_customer_id")
        val ACTIVE_WORKER_ID = stringPreferencesKey("active_worker_id")
        val SHOW_SAMPLE_DATA = booleanPreferencesKey("show_sample_preview_data")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val FAVORITE_WORKER_IDS = stringPreferencesKey("favorite_worker_ids_list")
    }

    val preferencesFlow: Flow<AppUserPreferences> = dataStore.data.map { prefs ->
        val rawFavorites = prefs[Keys.FAVORITE_WORKER_IDS].orEmpty()
        val parsedFavorites = if (rawFavorites.isBlank()) {
            emptyList()
        } else {
            rawFavorites.split("|").map { it.trim() }.filter { it.isNotEmpty() }.distinct()
        }
        AppUserPreferences(
            language = AppLanguage.fromCode(prefs[Keys.LANGUAGE] ?: AppLanguage.ENGLISH.code),
            activeRole = prefs[Keys.ACTIVE_ROLE]?.let {
                runCatching { UserRole.valueOf(it) }.getOrNull()
            } ?: UserRole.CUSTOMER,
            activeCustomerId = prefs[Keys.ACTIVE_CUSTOMER_ID] ?: "cust_local_1",
            activeWorkerId = prefs[Keys.ACTIVE_WORKER_ID] ?: "wrk_local_1",
            showSamplePreviewData = prefs[Keys.SHOW_SAMPLE_DATA] ?: true,
            darkTheme = prefs[Keys.DARK_THEME] ?: false,
            favoriteWorkerIds = parsedFavorites
        )
    }

    suspend fun setLanguage(language: AppLanguage) {
        dataStore.edit { it[Keys.LANGUAGE] = language.code }
    }

    suspend fun setActiveRole(role: UserRole) {
        dataStore.edit { it[Keys.ACTIVE_ROLE] = role.name }
    }

    suspend fun setShowSamplePreviewData(show: Boolean) {
        dataStore.edit { it[Keys.SHOW_SAMPLE_DATA] = show }
    }

    suspend fun setDarkTheme(dark: Boolean) {
        dataStore.edit { it[Keys.DARK_THEME] = dark }
    }

    suspend fun setFavoriteWorkerIds(ids: List<String>) {
        val serialized = ids.map { it.trim() }.filter { it.isNotEmpty() }.distinct().joinToString("|")
        dataStore.edit { it[Keys.FAVORITE_WORKER_IDS] = serialized }
    }
}
