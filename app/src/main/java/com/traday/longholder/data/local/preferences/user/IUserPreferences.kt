package com.traday.longholder.data.local.preferences.user

interface IUserPreferences {

    suspend fun setUserToken(userToken: String?): Unit

    suspend fun getUserToken(): String?

    suspend fun setOnboardingPassed(isPassed: Boolean): Unit

    suspend fun isOnboardingPassed(): Boolean
}