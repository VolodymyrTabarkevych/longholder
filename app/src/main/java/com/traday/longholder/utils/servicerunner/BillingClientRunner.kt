package com.traday.longholder.utils.servicerunner

import android.content.Context
import com.android.billingclient.api.*
import com.traday.longholder.data.mapper.result
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.ErrorHandler
import com.traday.longholder.domain.servicerunner.IBillingClientRunner
import com.traday.longholder.extensions.safeConnection
import com.traday.longholder.utils.EMPTY_STRING
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingClientRunner @Inject constructor(
    @ApplicationContext context: Context
) : IBillingClientRunner {

    private val context = context.applicationContext

    private val errorHandler = ErrorHandler()

    private lateinit var billingClient: BillingClient

    override suspend fun initBillingClient(purchasesUpdatedListener: PurchasesUpdatedListener) {
        this.billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
    }

    override suspend fun getSubscription(): Resource<Purchase> = result {
        billingClient.safeConnection {
            val productDetailsResult = billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder()
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
            productDetailsResult.purchasesList.first()
        }
    }.toResource(errorHandler)


    override suspend fun getProductDetailsParams(products: List<QueryProductDetailsParams.Product>): Resource<Pair<BillingClient, BillingFlowParams>> =
        result {
            billingClient.safeConnection {
                val productDetailsResult = billingClient.queryProductDetails(
                    QueryProductDetailsParams.newBuilder()
                        .setProductList(products)
                        .build()
                )
                val productDetailsParamsList =
                    productDetailsResult.productDetailsList?.mapIndexed { _, productDetails ->
                        val offerToken =
                            productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken
                                ?: EMPTY_STRING
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .setOfferToken(offerToken)
                            .build()
                    }

                return@safeConnection billingClient to BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList!!)
                    .build()
            }
        }.toResource(errorHandler)

    override suspend fun disconnect(): Resource<Unit> = result {
        billingClient.endConnection()
    }.toResource(errorHandler)
}