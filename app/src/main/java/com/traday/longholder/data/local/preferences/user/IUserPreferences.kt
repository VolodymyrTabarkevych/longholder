package com.traday.longholder.data.local.preferences.user

import com.traday.longholder.domain.enums.UserStatus
import kotlinx.coroutines.flow.Flow

interface IUserPreferences {

    suspend fun setUserToken(userToken: String?): Unit

    suspend fun getUserToken(): String?

    suspend fun setOnboardingPassed(isPassed: Boolean): Unit

    suspend fun isOnboardingPassed(): Boolean

    fun getUserStatus(): Flow<UserStatus>
}