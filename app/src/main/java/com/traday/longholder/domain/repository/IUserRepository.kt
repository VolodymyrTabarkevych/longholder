package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.UserEntity
import com.traday.longholder.domain.enums.UserStatus
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    suspend fun getUser(sync: Boolean): Result<UserEntity>

    fun subscribeOnUser(syncOnStart: Boolean): Flow<Result<UserEntity>>

    suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit>

    fun getUserStatus(): Flow<Result<UserStatus>>
}