package com.traday.longholder.data.remote.datasource.report

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.ReportDto

interface IReportRemoteDataSource {

    suspend fun getReport(currencyId: String): Result<ReportDto>
}