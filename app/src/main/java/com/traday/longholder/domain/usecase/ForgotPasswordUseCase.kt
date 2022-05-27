package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IAuthenticateRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authenticateRepository: IAuthenticateRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<ForgotPasswordUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return authenticateRepository.forgotPassword(params.email).toResource(errorHandler)
    }

    class Params(val email: String) : EmptyParams()
}