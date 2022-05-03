package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Report
import com.traday.longholder.domain.repository.IReportRepository
import javax.inject.Inject

class GetReportUseCase @Inject constructor(
    private val reportRepository: IReportRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<GetReportUseCase.Params, Report>() {

    override suspend fun run(params: Params): Resource<Report> {
        return reportRepository.getReport(params.currencyId)
            .toResource(errorHandler) { it.toDomain() }
    }

    class Params(val currencyId: String) : EmptyParams()
}