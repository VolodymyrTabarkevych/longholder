package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserStatusUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<EmptyParams, UserStatus>() {

    override fun run(params: EmptyParams): Flow<Resource<UserStatus>> {
        return userRepository.getUserStatus().toResource(errorHandler)
    }
}