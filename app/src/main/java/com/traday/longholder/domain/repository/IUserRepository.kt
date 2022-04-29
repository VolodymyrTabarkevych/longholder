package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.local.entity.UserEntity

interface IUserRepository {

    suspend fun getUser(sync: Boolean): Result<UserEntity>

    suspend fun getUserToken(): Result<String>

    suspend fun logout(): Result<Unit>

    suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit>

    suspend fun isOnboardingPassed(): Result<Boolean>
}