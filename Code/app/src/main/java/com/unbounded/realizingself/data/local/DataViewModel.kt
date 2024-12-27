package com.peak.unbounded.features.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unbounded.realizingself.data.UserSettings
import com.unbounded.realizingself.data.local.AppConstants.DATE_OF_JOIN
import com.unbounded.realizingself.data.local.AppConstants.IS_Time_ELAPSED
import com.unbounded.realizingself.data.local.AppConstants.QUICK_TIPS
import com.unbounded.realizingself.data.local.AppConstants.USER_EMAIL
import com.unbounded.realizingself.data.local.AppConstants.USER_F_NAME
import com.unbounded.realizingself.data.local.AppConstants.USER_ID
import com.unbounded.realizingself.data.local.AppConstants.USER_IMAGE
import com.unbounded.realizingself.data.local.AppConstants.USER_IS_ONBOARDING
import com.unbounded.realizingself.data.local.AppConstants.USER_L_NAME
import com.unbounded.realizingself.data.local.AppConstants.USER_NAME
import com.unbounded.realizingself.data.local.AppConstants.USER_SETTINGS
import com.unbounded.realizingself.data.local.AppConstants.USER_TOKEN
import com.unbounded.realizingself.data.local.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel()
{

    fun saveUserEmail(value: String) {
        viewModelScope.launch {
            repository.putString(USER_EMAIL, value)
        }
    }
    fun getUserEmail(): String? = runBlocking {
        repository.getString(USER_EMAIL)
    }
    fun saveUserFullName(value: String) {
        viewModelScope.launch {
            repository.putString(USER_NAME, value)
        }
    }
    fun getUserFullName(): String? = runBlocking {
        repository.getString(USER_EMAIL)
    }

    fun saveUserFName(value: String) {
        viewModelScope.launch {
            repository.putString(USER_F_NAME, value)
        }
    }
    fun getUserFName(): String? = runBlocking {
        repository.getString(USER_F_NAME)
    }

    fun saveUserLName(value: String) {
        viewModelScope.launch {
            repository.putString(USER_L_NAME, value)
        }
    }
    fun getUserLName(): String? = runBlocking {
        repository.getString(USER_L_NAME)
    }
    fun saveUserImage(value: String) {
        viewModelScope.launch {
            repository.putString(USER_IMAGE, value)
        }
    }
    fun getUserImage(): String? = runBlocking {
        repository.getString(USER_IMAGE)
    }
    fun saveDateOfJoin(value: String) {
        viewModelScope.launch {
            repository.putString(DATE_OF_JOIN, value)
        }
    }
    fun getDateOfJoin(): String? = runBlocking {
        repository.getString(DATE_OF_JOIN)
    }
    // In your DataStoreViewModel

    fun saveUserId(value: Int) {
        viewModelScope.launch {
            repository.putInt(USER_ID, value)
        }
    }
    fun getUserId(): Int? = runBlocking {
        repository.getInt(USER_ID)
    }

    fun saveIsOnBoarding(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(USER_IS_ONBOARDING, value)
        }
    }
    fun getIsOnBoarding(): Boolean? = runBlocking {
        repository.getBoolean(USER_IS_ONBOARDING)
    }
    fun getIsTimeElapsed(): Boolean? = runBlocking {
        repository.getBoolean(IS_Time_ELAPSED)
    }
    fun saveIsTimeElapsed(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(IS_Time_ELAPSED, value)
        }
    }
    fun saveToken(value: String) {
        viewModelScope.launch {
            repository.putString(USER_TOKEN, value)
        }
    }
    fun getToken(): String? = runBlocking {
        repository.getString(USER_TOKEN)
    }
    fun saveUserSettings(value: UserSettings) {
        viewModelScope.launch {
            repository.putUserSettings(USER_SETTINGS, value)
        }
    }
    fun getUserSettings(): UserSettings = runBlocking {
        repository.getUserSettings(USER_SETTINGS)
    }
    fun saveQuickTips(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(QUICK_TIPS, value)
        }
    }
    fun getQuickTips():  Boolean? = runBlocking {
        repository.getBoolean(QUICK_TIPS)
    }




  /*  fun saveTimelapseTimer(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(USER_IS_ONBOARDING, value)
        }
    }
    fun getTimelapseTimer(): Boolean? = runBlocking {
        repository.getBoolean(USER_IS_ONBOARDING)
    }
    fun saveShareStats(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(USER_IS_ONBOARDING, value)
        }
    }
    fun getShareStats(): Boolean? = runBlocking {
        repository.getBoolean(USER_IS_ONBOARDING)
    }
    fun saveShareStats(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(USER_IS_ONBOARDING, value)
        }
    }
    fun getShareStats(): Boolean? = runBlocking {
        repository.getBoolean(USER_IS_ONBOARDING)
    }*/
}