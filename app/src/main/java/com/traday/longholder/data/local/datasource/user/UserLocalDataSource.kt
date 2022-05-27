package com.traday.longholder.data.local.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.database.dao.UserDao
import com.traday.longholder.data.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.data.local.entity.UserEntity
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.mapper.flowResult
import com.traday.longholder.data.mapper.result
import com.traday.longholder.domain.enums.UserStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userPreferences: IUserPreferences,
    private val userDao: UserDao
) : BaseLocalDataSource(), IUserLocalDataSource {

    override suspend fun setUserToken(userToken: String?): Result<Unit> =
        result { userPreferences.setUserToken(userToken) }

    override suspend fun setUser(userEntity: UserEntity?): Result<Unit> =
        result { userEntity?.let { user -> userDao.insertData(user) } ?: userDao.deleteUser() }

    override suspend fun getUser(): Result<UserEntity> =
        result { userDao.getUser() }

    override fun subscribeOnUser(): Flow<Result<UserEntity>> =
        flowResult { userDao.subscribeOnUser() }

    override suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit> =
        result { userPreferences.setOnboardingPassed(isPassed) }

    override fun getUserStatus(): Flow<Result<UserStatus>> =
        flowResult { userPreferences.getUserStatus() }
}