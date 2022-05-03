package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.ReportDto

interface IReportRepository {

    suspend fun getReport(currencyId: String): Result<ReportDto>
}