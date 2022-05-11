package com.traday.longholder.data.remote.datasource.report

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.mapper.apiResult
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.dto.ReportDto
import com.traday.longholder.data.remote.rest.IRestBuilder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class ReportRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<ReportRemoteDataSource.API>(apiBuilder, preferences),
    IReportRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun getReport(currencyId: String): Result<ReportDto> = apiResult {
        api.getReport(currencyId)
    }

    interface API {

        @GET("User/report/{currencyId}")
        suspend fun getReport(@Path("currencyId") currencyId: String): Response<ReportDto>
    }
}