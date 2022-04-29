package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IUserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<EmptyParams, Unit>() {

    override suspend fun run(params: EmptyParams): Resource<Unit> {
        return userRepository.logout().toResource(errorHandler)
    }
}