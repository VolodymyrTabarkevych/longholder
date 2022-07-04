package com.traday.longholder.data.remote.datasource.subscription

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.mapper.apiResult
import com.traday.longholder.data.mapper.result
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.requestbody.CreateSubscriptionRequestBody
import com.traday.longholder.data.remote.responsebody.CheckIfSubscribedResponseBody
import com.traday.longholder.data.remote.rest.IRestBuilder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject

class SubscriptionRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<SubscriptionRemoteDataSource.API>(apiBuilder, preferences),
    ISubscriptionRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun create(
        packageName: String,
        productId: String,
        purchaseToken: String,
        deviceName: String
    ): Result<Unit> = apiResult {
        api.create(
            CreateSubscriptionRequestBody(
                subscriptionId = productId,
                packageName = packageName,
                userToken = purchaseToken,
                deviceName = deviceName
            )
        )
    }

    override suspend fun stop(): Result<Unit> = apiResult {
        api.stop()
    }

    override suspend fun isUserHasSubscription(): Result<Boolean> = result {
        api.isUserHasSubscription().body()!!.value
    }

    override suspend fun getSubscriptions(): Result<List<String>> = apiResult {
        api.getSubscriptions()
    }

    interface API {

        @POST("Subscription/create")
        suspend fun create(@Body createSubscriptionRequestBody: CreateSubscriptionRequestBody): Response<Unit>

        @GET("Subscription/stop")
        suspend fun stop(): Response<Unit>

        @GET("Subscription/checkifsubscribed")
        suspend fun isUserHasSubscription(): Response<CheckIfSubscribedResponseBody>

        @GET("subscription/Google/available")
        suspend fun getSubscriptions(): Response<List<String>>
    }
}