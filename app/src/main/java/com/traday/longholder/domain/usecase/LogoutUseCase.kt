package com.traday.longholder.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.traday.longholder.data.local.database.AppDatabase
import com.traday.longholder.di.qualifire.SubscriptionPreferences
import com.traday.longholder.di.qualifire.UserPreferences
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    @UserPreferences private val userPreferences: DataStore<Preferences>,
    @SubscriptionPreferences private val subscriptionPreferences: DataStore<Preferences>,
    private val dataBase: AppDatabase
) : BaseUseCase<EmptyParams, Unit>() {

    override suspend fun run(params: EmptyParams): Resource<Unit> =
        withContext(Dispatchers.IO) {
            dataBase.clearAllTables()
            userPreferences.edit { it.clear() }
            subscriptionPreferences.edit { it.clear() }
            Resource.Success(Unit)
        }

}