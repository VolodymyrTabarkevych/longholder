package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result

interface IAuthenticateRepository {

    suspend fun register(userName: String, email: String, password: String): Result<Unit>

    suspend fun login(userName: String, password: String): Result<Unit>
}