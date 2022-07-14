package com.traday.longholder.domain.usecase

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.QueryProductDetailsParams
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.ISubscriptionRepository
import javax.inject.Inject

class GetSubscriptionsUseCase @Inject constructor(
    private val subscriptionRepository: ISubscriptionRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<EmptyParams, List<QueryProductDetailsParams.Product>>() {

    override suspend fun run(params: EmptyParams): Resource<List<QueryProductDetailsParams.Product>> {
        return subscriptionRepository.getSubscriptions().toResource(errorHandler) {
            it.map { subscription ->
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(subscription)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            }
        }
    }
}