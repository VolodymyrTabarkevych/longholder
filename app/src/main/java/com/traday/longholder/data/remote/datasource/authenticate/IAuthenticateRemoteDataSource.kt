package com.traday.longholder.data.remote.datasource.authenticate

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.responsebody.LoginResponseBody

interface IAuthenticateRemoteDataSource {

    suspend fun register(email: String, password: String): Result<Unit>

    suspend fun login(email: String, password: String): Result<LoginResponseBody>

    suspend fun forgotPassword(email: String): Result<Unit>
}