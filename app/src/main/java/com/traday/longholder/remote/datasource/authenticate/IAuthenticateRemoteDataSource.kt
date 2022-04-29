package com.traday.longholder.remote.datasource.authenticate

import com.traday.longholder.data.base.Result
import com.traday.longholder.remote.responsebody.authenticate.LoginResponseBody

interface IAuthenticateRemoteDataSource {

    suspend fun register(userName: String, email: String, password: String): Result<Unit>

    suspend fun login(userName: String, password: String): Result<LoginResponseBody>
}