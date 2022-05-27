package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result

interface IAuthenticateRepository {

    suspend fun register(email: String, password: String): Result<Unit>

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun forgotPassword(email: String): Result<Unit>
}