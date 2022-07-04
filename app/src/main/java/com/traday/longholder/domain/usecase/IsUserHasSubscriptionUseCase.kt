package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.ISubscriptionRepository
import javax.inject.Inject

class IsUserHasSubscriptionUseCase @Inject constructor(
    private val subscriptionRepository: ISubscriptionRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<IsUserHasSubscriptionUseCase.Params, Boolean>() {

    override suspend fun run(params: Params): Resource<Boolean> {
        return subscriptionRepository.isUserHasSubscription(params.sync).toResource(errorHandler)
    }

    class Params(val sync: Boolean) : EmptyParams()
}