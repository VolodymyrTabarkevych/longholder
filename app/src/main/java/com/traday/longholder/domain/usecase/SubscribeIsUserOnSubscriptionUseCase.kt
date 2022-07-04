package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.ISubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeIsUserOnSubscriptionUseCase @Inject constructor(
    private val subscriptionRepository: ISubscriptionRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<EmptyParams, Boolean>() {

    override fun run(params: EmptyParams): Flow<Resource<Boolean>> {
        return subscriptionRepository.subscribeOnIsUserHasSubscription().toResource(errorHandler)
    }
}