package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.ICalcRepository
import javax.inject.Inject

class NumberFromUseCase @Inject constructor(
    private val calcRepository: ICalcRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<NumberFromUseCase.Params, String>() {

    override suspend fun run(params: Params): Resource<String> {
        return calcRepository.numberFromNumber(
            numberThat = params.numberThat,
            numberFrom = params.numberFrom
        ).toResource(errorHandler)
    }

    class Params(val numberThat: Double, val numberFrom: Double) : EmptyParams()
}