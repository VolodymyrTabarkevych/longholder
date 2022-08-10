package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IActiveRepository
import javax.inject.Inject

class CreateActiveUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<CreateActiveUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return activeRepository.createActive(
            name = params.name,
            priceOnStart = params.priceOnStart,
            valueOfCrypto = params.valueOfCrypto,
            wantedPercents = params.wantedPercents,
            currentCurrencyPrice = params.currentCurrencyPrice,
            dateOfEnd = params.dateOfEnd,
            comment = params.comment,
            linkToImage = params.linkToImage,
            symbol = params.symbol,
            indexOnExchange = params.indexOnExchange
        ).toResource(errorHandler)
    }

    class Params(
        val name: String?,
        val priceOnStart: String,
        val valueOfCrypto: String,
        val wantedPercents: String,
        val currentCurrencyPrice: Double,
        val dateOfEnd: String,
        val comment: String?,
        val linkToImage: String?,
        val symbol: String,
        val indexOnExchange: String
    ) : EmptyParams()
}