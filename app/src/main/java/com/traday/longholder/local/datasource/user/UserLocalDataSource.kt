package com.traday.longholder.local.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.extensions.safeOperation
import com.traday.longholder.local.database.dao.UserDao
import com.traday.longholder.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.local.entity.UserEntity
import com.traday.longholder.local.preferences.user.IUserPreferences
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userPreferences: IUserPreferences,
    private val userDao: UserDao
) : BaseLocalDataSource(), IUserLocalDataSource {

    override suspend fun setUserToken(userToken: String?): Result<Unit> =
        safeOperation { userPreferences.setUserToken(userToken) }

    override suspend fun getUserToken(): Result<String> =
        safeOperation { userPreferences.getUserToken() }

    override suspend fun setUser(userEntity: UserEntity?): Result<Unit> = safeOperation {
        userEntity?.let { user -> userDao.insertData(user) } ?: userDao.deleteUser()
    }

    override suspend fun getUser(): Result<UserEntity> = safeOperation { userDao.getUser() }

    override suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit> =
        safeOperation { userPreferences.setOnboardingPassed(isPassed) }

    override suspend fun isOnboardingPassed(): Result<Boolean> =
        safeOperation { userPreferences.isOnboardingPassed() }
}