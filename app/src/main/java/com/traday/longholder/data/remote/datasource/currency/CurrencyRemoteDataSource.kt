package com.traday.longholder.data.remote.datasource.currency

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.mapper.apiResult
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.dto.CurrencyDto
import com.traday.longholder.data.remote.rest.IRestBuilder
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

    override suspend fun getCurrencies(): Result<List<CurrencyDto>> = apiResult {
        api.getCurrencies()
    }

    override suspend fun getUserCurrencies(): Result<List<CurrencyDto>> = apiResult {
        api.getUserCurrencies()
    }

    interface API {

        @GET("Currency/allcrypto")
        suspend fun getCurrencies(): Response<List<CurrencyDto>>

        @GET("User/report/getReportedValues")
        suspend fun getUserCurrencies(): Response<List<CurrencyDto>>
    }
}