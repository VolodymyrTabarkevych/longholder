package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.ICalcRepository
import javax.inject.Inject

class PercentFromUseCase @Inject constructor(
    private val calcRepository: ICalcRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<PercentFromUseCase.Params, String>() {

    override suspend fun run(params: Params): Resource<String> {
        return calcRepository.percentFrom(params.percent, params.number).toResource(errorHandler)
    }

    class Params(val percent: Double, val number: Double) : EmptyParams()
}