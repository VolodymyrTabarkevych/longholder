package com.traday.longholder.data.local.preferences.subscription

import kotlinx.coroutines.flow.Flow

interface ISubscriptionPreferences {

    suspend fun setUserHasSubscription(hasSubscription: Boolean)

    suspend fun isUserHasSubscription(): Boolean

    fun subscribeOnIsUserHasSubscription(): Flow<Boolean>
}