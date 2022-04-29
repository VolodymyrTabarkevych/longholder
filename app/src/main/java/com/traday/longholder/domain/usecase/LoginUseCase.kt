package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IAuthenticateRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticateRepository: IAuthenticateRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<LoginUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return authenticateRepository.login(
            userName = params.userName,
            password = params.password
        ).toResource(errorHandler)
    }

    class Params(val userName: String, val password: String) : EmptyParams()
}