package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.data.remote.requestbody.CreateActiveRequestBody
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IActiveRepository
import com.traday.longholder.extensions.formatDateClientFormatToServerFormatOrEmpty
import javax.inject.Inject

class CreateActiveUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<CreateActiveUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return activeRepository.createActive(
            CreateActiveRequestBody(
                name = params.name,
                valueOfCrypto = params.valueOfCrypto.toDouble(),
                currentCurrencyPrice = params.currentCurrencyPrice,
                cryptoPriceOnStart = params.cryptoPriceOnStart,
                priceInOtherCurrencyOnStart = calculatePriceInOtherCurrencyOnStart(
                    params.cryptoPriceOnStart,
                    params.valueOfCrypto.toDouble()
                ),
                dateOfEnd = params.dateOfEnd.formatDateClientFormatToServerFormatOrEmpty(),
                comment = params.comment,
                linkToImage = params.linkToImage
            )
        ).toResource(errorHandler)
    }

    private fun calculatePriceInOtherCurrencyOnStart(
        cryptoPriceOnStart: Double,
        valueOfCrypto: Double
    ): Double {
        return cryptoPriceOnStart * valueOfCrypto
    }

    class Params(
        val name: String?,
        val valueOfCrypto: String,
        val currentCurrencyPrice: Double,
        val cryptoPriceOnStart: Double,
        val dateOfEnd: String,
        val comment: String?,
        val linkToImage: String?
    ) : EmptyParams()
}