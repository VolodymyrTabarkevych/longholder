package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.domain.repository.IAuthenticateRepository
import com.traday.longholder.local.datasource.user.IUserLocalDataSource
import com.traday.longholder.remote.datasource.authenticate.IAuthenticateRemoteDataSource
import javax.inject.Inject

class AuthenticateRepository @Inject constructor(
    private val authenticateRemoteDataSource: IAuthenticateRemoteDataSource,
    private val userLocalDataSource: IUserLocalDataSource
) : IAuthenticateRepository {

    override suspend fun register(userName: String, email: String, password: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun login(userName: String, password: String): Result<Unit> {
        return when (val result = authenticateRemoteDataSource.login(userName, password)) {
            is Result.Error -> {
                result
            }
            is Result.Success -> {
                val token = result.data.token
                userLocalDataSource.setUserToken(token)
                Result.Success(Unit)
            }
        }
    }
}