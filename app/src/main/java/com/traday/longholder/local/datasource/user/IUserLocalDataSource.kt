package com.traday.longholder.local.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.local.entity.UserEntity

interface IUserLocalDataSource {

    suspend fun setUserToken(userToken: String?): Result<Unit>

    suspend fun getUserToken(): Result<String>

    suspend fun setUser(userEntity: UserEntity?): Result<Unit>

    suspend fun getUser(): Result<UserEntity>

    suspend fun setOnboardingPassed(isPassed: Boolean): Result<Unit>

    suspend fun isOnboardingPassed(): Result<Boolean>
}