package com.traday.longholder.data.local.preferences.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.traday.longholder.data.local.preferences.base.BasePreferences
import com.traday.longholder.di.qualifire.UserPreferences
import javax.inject.Inject

class UserPreferences @Inject constructor(
    @UserPreferences dataStore: DataStore<Preferences>
) : BasePreferences(dataStore), IUserPreferences {

    override suspend fun setUserToken(userToken: String?): Unit = write {
        userToken?.let { token -> it[USER_TOKEN] = token } ?: it.remove(USER_TOKEN)
    }

    override suspend fun getUserToken(): String? = read { it[USER_TOKEN] }

    override suspend fun setOnboardingPassed(isPassed: Boolean) = write {
        it[IS_ONBOARDING_PASSED] = isPassed
    }

    override suspend fun isOnboardingPassed(): Boolean = read {
        it[IS_ONBOARDING_PASSED] ?: false
    }

    companion object {

        private val USER_TOKEN = stringPreferencesKey("user_token")
        private val IS_ONBOARDING_PASSED = booleanPreferencesKey("onboarding_passed")
    }
}