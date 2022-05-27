package com.traday.longholder.data.local.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.UserEntity
import com.traday.longholder.domain.enums.UserStatus
import kotlinx.coroutines.flow.Flow

interface IUserLocalDataSource {

    suspend fun setUserToken(userToken: String?): Result<Unit>

    suspend fun setUser(userEntity: UserEntity?): Result<Unit>

    suspend fun getUser(): Result<UserEntity>

    fun subscribeOnUser(): Flow<Result<UserEntity>>

    suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit>

    fun getUserStatus(): Flow<Result<UserStatus>>
}