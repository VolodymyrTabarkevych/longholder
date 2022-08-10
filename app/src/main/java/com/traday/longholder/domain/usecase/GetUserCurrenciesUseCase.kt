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

class GetUserCurrenciesUseCase @Inject constructor(
    private val currencyRepository: ICurrencyRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<EmptyParams, List<Currency>>() {

    override suspend fun run(params: EmptyParams): Resource<List<Currency>> {
        return currencyRepository.getUserCurrencies()
            .toResource(errorHandler) { list ->
                list.mapIndexed { index, currencyDto ->
                    currencyDto.toDomain(
                        index
                    )
                }
            }
    }
}