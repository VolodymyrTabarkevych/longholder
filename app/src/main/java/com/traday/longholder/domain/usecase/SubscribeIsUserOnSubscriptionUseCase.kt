package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeIsUserOnSubscriptionUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<SubscribeIsUserOnSubscriptionUseCase.Params, Boolean>() {

    override fun run(params: Params): Flow<Resource<Boolean>> {
        return userRepository.subscribeOnUser(params.syncOnStart)
            .toResource(errorHandler) { it.isOnSubscription }
    }

    class Params(val syncOnStart: Boolean) : EmptyParams()
}