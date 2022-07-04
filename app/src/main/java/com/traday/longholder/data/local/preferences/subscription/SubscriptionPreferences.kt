package com.traday.longholder.data.local.preferences.subscription

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.traday.longholder.data.local.preferences.base.BasePreferences
import com.traday.longholder.di.qualifire.SubscriptionPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscriptionPreferences @Inject constructor(
    @SubscriptionPreferences dataStore: DataStore<Preferences>
) : BasePreferences(dataStore), ISubscriptionPreferences {

    override suspend fun setUserHasSubscription(hasSubscription: Boolean) = write {
        it[IS_USER_HAS_SUBSCRIPTION] = hasSubscription
    }

    override suspend fun isUserHasSubscription(): Boolean = read {
        it[IS_USER_HAS_SUBSCRIPTION] ?: false
    }

    override fun subscribeOnIsUserHasSubscription(): Flow<Boolean> = subscribe {
        it[IS_USER_HAS_SUBSCRIPTION] ?: false
    }

    companion object {

        private val IS_USER_HAS_SUBSCRIPTION = booleanPreferencesKey("is_user_has_subscription")
    }
}