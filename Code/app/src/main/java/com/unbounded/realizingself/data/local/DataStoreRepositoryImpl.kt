package com.unbounded.realizingself.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.unbounded.realizingself.data.UserSettings

import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "my_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

 class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }


    private val gson = Gson()

    override suspend fun putUserSettings(key: String,userSettings: UserSettings) {
        val preferencesKey = stringPreferencesKey(key)
        val json = gson.toJson(userSettings)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = json
        }
    }

    override suspend fun getUserSettings(key: String): UserSettings {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            val json = preferences[preferencesKey] ?: throw Exception("No data found for key: $key") // Throw an exception if data not found
            gson.fromJson(json, UserSettings::class.java) // Deserialize JSON string to UserSettings
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception appropriately, such as logging and returning a default value
            // For now, let's return a default UserSettings instance
            UserSettings(
                connectToAppleHealthKit = false,
                emailNotifications = false,
                isPrivate = false,
                pushNotifications = false,
                shareMilestones = false,
                shareStats = false,
                timelapseTimer = false
            )
        }
    }


    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception) {
            e.printStackTrace()
            null
        }
        }
    override suspend fun getInt(key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return try {
            val preferencesKey = booleanPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}