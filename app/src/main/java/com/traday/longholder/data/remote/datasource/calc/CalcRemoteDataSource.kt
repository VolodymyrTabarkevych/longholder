package com.traday.longholder.data.remote.datasource.calc

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.rest.IRestBuilder
import com.traday.longholder.extensions.apiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class CalcRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<CalcRemoteDataSource.API>(apiBuilder, preferences),
    ICalcRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun percentFrom(percent: Double, number: Double): Result<String> =
        apiResult { api.percentFrom(percent, number) }

    override suspend fun numberFromNumber(numberThat: Double, numberFrom: Double): Result<String> =
        apiResult { api.numberFromNumber(numberThat, numberFrom) }

    override suspend fun addPercent(percent: Double, number: Double): Result<String> =
        apiResult { api.addPercent(percent, number) }

    override suspend fun subtractPercent(percent: Double, number: Double): Result<String> =
        apiResult { api.subtractPercent(percent, number) }

    interface API {

        @GET("Calc/percentFrom/{percent}/{number}")
        suspend fun percentFrom(
            @Path("percent") percent: Double,
            @Path("number") number: Double
        ): Response<String>

        @GET("Calc/numberFromNumber/{numberThat}/{numberFrom}")
        suspend fun numberFromNumber(
            @Path("numberThat") numberThat: Double,
            @Path("numberFrom") numberFrom: Double
        ): Response<String>

        @GET("Calc/addPercent/{percent}/{number}")
        suspend fun addPercent(
            @Path("percent") percent: Double,
            @Path("number") number: Double
        ): Response<String>

        @GET("Calc/dividePercent/{percent}/{number}")
        suspend fun subtractPercent(
            @Path("percent") percent: Double,
            @Path("number") number: Double
        ): Response<String>
    }
}