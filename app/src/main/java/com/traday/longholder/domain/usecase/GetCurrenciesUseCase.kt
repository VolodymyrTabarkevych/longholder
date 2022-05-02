package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.repository.ICurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currencyRepository: ICurrencyRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<EmptyParams, List<Currency>>() {

    override fun run(params: EmptyParams): Flow<Resource<List<Currency>>> {
        return currencyRepository.getCurrencies()
            .toResource(errorHandler) { currencies -> currencies.map { it.toDomain() } }
    }
}