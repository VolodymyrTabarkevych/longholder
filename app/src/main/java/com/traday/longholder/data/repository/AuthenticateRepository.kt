package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.user.IUserLocalDataSource
import com.traday.longholder.data.remote.datasource.authenticate.IAuthenticateRemoteDataSource
import com.traday.longholder.domain.repository.IAuthenticateRepository
import javax.inject.Inject

class AuthenticateRepository @Inject constructor(
    private val authenticateRemoteDataSource: IAuthenticateRemoteDataSource,
    private val userLocalDataSource: IUserLocalDataSource
) : IAuthenticateRepository {

    override suspend fun register(userName: String, email: String, password: String): Result<Unit> {
        return authenticateRemoteDataSource.register(userName, email, password)
    }

    override suspend fun login(userName: String, password: String): Result<Unit> {
        val loginResult = authenticateRemoteDataSource.login(userName, password)
        if (loginResult is Result.Error) return loginResult
        val token = (loginResult as Result.Success).data.token
        return userLocalDataSource.setUserToken(token)
    }
}