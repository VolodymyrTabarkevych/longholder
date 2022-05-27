package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.user.IUserLocalDataSource
import com.traday.longholder.data.local.entity.UserEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.user.IUserRemoteDataSource
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: IUserRemoteDataSource,
    private val userLocalDataSource: IUserLocalDataSource
) : IUserRepository {

    override suspend fun getUser(sync: Boolean): Result<UserEntity> {
        if (sync) {
            val remoteUserResult = userRemoteDataSource.getUser()
            if (remoteUserResult is Result.Success) {
                val mappedUser = remoteUserResult.data.toEntity()
                userLocalDataSource.setUser(mappedUser)
            }
        }
        return userLocalDataSource.getUser()
    }

    override fun subscribeOnUser(syncOnStart: Boolean): Flow<Result<UserEntity>> {
        return userLocalDataSource.subscribeOnUser()
            .onStart {
                if (!syncOnStart) return@onStart
                val remoteUserResult = userRemoteDataSource.getUser()
                if (remoteUserResult is Result.Success) {
                    val mappedUser = remoteUserResult.data.toEntity()
                    userLocalDataSource.setUser(mappedUser)
                } else if (remoteUserResult is Result.Error) {
                    emit(remoteUserResult)
                }
            }
    }

    override suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit> {
        return userLocalDataSource.setOnboardingPassed(isPassed)
    }

    override fun getUserStatus(): Flow<Result<UserStatus>> {
        return userLocalDataSource.getUserStatus()
    }
}