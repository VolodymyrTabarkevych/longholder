package com.traday.longholder.domain.usecase

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.User
import com.traday.longholder.domain.repository.IAuthenticateRepository
import com.traday.longholder.domain.repository.IUserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticateRepository: IAuthenticateRepository,
    private val userRepository: IUserRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<LoginUseCase.Params, User>() {

    override suspend fun run(params: Params): Resource<User> {
        val result = authenticateRepository.login(
            email = params.email,
            password = params.password
        )
        if (result is Result.Error) return result.toResource(errorHandler)
        return userRepository.getUser(true).toResource(errorHandler) { it.toDomain() }
    }

    class Params(val email: String, val password: String) : EmptyParams()
}