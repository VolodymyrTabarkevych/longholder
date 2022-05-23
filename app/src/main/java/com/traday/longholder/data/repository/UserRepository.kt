package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.user.IUserLocalDataSource
import com.traday.longholder.data.local.entity.UserEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.user.IUserRemoteDataSource
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
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

    override suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit> {
        return userLocalDataSource.setOnboardingPassed(isPassed)
    }

    override fun getUserStatus(): Flow<Result<UserStatus>> {
        return userLocalDataSource.getUserStatus()
    }
}