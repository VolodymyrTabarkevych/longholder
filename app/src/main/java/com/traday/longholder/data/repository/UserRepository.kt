package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.domain.repository.IUserRepository
import com.traday.longholder.local.datasource.user.IUserLocalDataSource
import com.traday.longholder.local.entity.UserEntity
import com.traday.longholder.remote.datasource.user.IUserRemoteDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: IUserRemoteDataSource,
    private val userLocalDataSource: IUserLocalDataSource
) : IUserRepository {

    override suspend fun getUser(sync: Boolean): Result<UserEntity> {
        if (sync) {
            val result = userRemoteDataSource.getUser()
            if (result is Result.Success) {
                userLocalDataSource.setUser(result.data.toEntity())
            }
        }
        return userLocalDataSource.getUser()
    }

    override suspend fun getUserToken(): Result<String> {
        return userLocalDataSource.getUserToken()
    }

    override suspend fun logout(): Result<Unit> {
        userLocalDataSource.setUser(null)
        userLocalDataSource.setOnboardingPassed(false)
        userLocalDataSource.setUserToken(null)
        return Result.Success(Unit)
    }

    override suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit> {
        return userLocalDataSource.setOnboardingPassed(isPassed)
    }

    override suspend fun isOnboardingPassed(): Result<Boolean> {
        return userLocalDataSource.isOnboardingPassed()
    }
}