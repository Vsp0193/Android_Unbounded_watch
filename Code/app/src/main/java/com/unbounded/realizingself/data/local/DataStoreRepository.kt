package com.unbounded.realizingself.data.local

import com.unbounded.realizingself.data.UserSettings


interface DataStoreRepository {

    suspend fun putString(key: String, value: String)
    suspend fun putUserSettings(key: String, value: UserSettings)
    suspend fun getUserSettings(key: String): UserSettings
    suspend fun putInt(key: String, value: Int)
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?
    suspend fun getBoolean(key: String): Boolean?
}