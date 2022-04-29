package com.traday.longholder.local.datasource.user

import com.traday.longholder.data.base.Result

interface IUserLocalDataSource {

    suspend fun setUserToken(userToken: String?): Result<Unit>

    suspend fun getUserToken(): Result<String>
}