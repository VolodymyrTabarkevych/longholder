package com.traday.longholder.local.preferences.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.traday.longholder.di.qualifire.UserPreferences
import com.traday.longholder.local.preferences.base.BasePreferences
import javax.inject.Inject

class UserPreferences @Inject constructor(
    @UserPreferences dataStore: DataStore<Preferences>
) : BasePreferences(dataStore), IUserPreferences {

    override suspend fun setUserToken(userToken: String?): Unit = write {
        userToken?.let { token -> it[USER_TOKEN] = token } ?: it.remove(USER_TOKEN)
    }

    override suspend fun getUserToken(): String? = read { it[USER_TOKEN] }

    companion object {

        private val USER_TOKEN = stringPreferencesKey("user_token")
    }
}