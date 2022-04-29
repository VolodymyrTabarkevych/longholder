package com.traday.longholder.local.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.extensions.safeOperation
import com.traday.longholder.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.local.preferences.user.IUserPreferences
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userPreferences: IUserPreferences
) : BaseLocalDataSource(), IUserLocalDataSource {

    override suspend fun setUserToken(userToken: String?): Result<Unit> = safeOperation {
        userPreferences.setUserToken(userToken)
    }

    override suspend fun getUserToken(): Result<String> = safeOperation {
        userPreferences.getUserToken()
    }
}