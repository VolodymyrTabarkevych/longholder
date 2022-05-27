package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.repository.ICurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currencyRepository: ICurrencyRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<GetCurrenciesUseCase.Params, List<Currency>>() {

    override suspend fun run(params: Params): Resource<List<Currency>> {
        return currencyRepository.getCurrencies(params.sync)
            .toResource(errorHandler) { currencies -> currencies.map { it.toDomain() } }
    }

    class Params(val sync: Boolean) : EmptyParams()
}