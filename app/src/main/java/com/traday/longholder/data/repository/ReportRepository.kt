package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.datasource.report.IReportRemoteDataSource
import com.traday.longholder.data.remote.dto.ReportDto
import com.traday.longholder.domain.repository.IReportRepository
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportRemoteDataSource: IReportRemoteDataSource
) : IReportRepository {

    override suspend fun getReport(currencyId: String): Result<ReportDto> {
        return reportRemoteDataSource.getReport(currencyId)
    }
}