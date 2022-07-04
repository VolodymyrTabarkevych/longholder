package com.traday.longholder.domain.servicerunner

import com.android.billingclient.api.*
import com.traday.longholder.domain.base.Resource

interface IBillingClientRunner {

    suspend fun initBillingClient(
        purchasesUpdatedListener: PurchasesUpdatedListener = PurchasesUpdatedListener { _, _ -> }
    )

    suspend fun getProductDetailsParams(products: List<QueryProductDetailsParams.Product>): Resource<Pair<BillingClient, BillingFlowParams>>

    suspend fun getSubscription(): Resource<Purchase>

    suspend fun disconnect(): Resource<Unit>
}