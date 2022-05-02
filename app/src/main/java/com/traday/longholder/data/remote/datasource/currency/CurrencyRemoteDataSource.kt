package com.traday.longholder.data.remote.datasource.currency

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.dto.CurrencyDto
import com.traday.longholder.data.remote.rest.IRestBuilder
import com.traday.longholder.extensions.safeApiCall
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<CurrencyRemoteDataSource.API>(apiBuilder, preferences),
    ICurrencyRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun getCurrencies(): Result<List<CurrencyDto>> = safeApiCall {
        api.getCurrencies()
    }

    interface API {

        @GET("Currency/allcrypto")
        suspend fun getCurrencies(): Response<List<CurrencyDto>>
    }
}