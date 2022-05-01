package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDto
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Crypto
import com.traday.longholder.domain.repository.ICryptoRepository
import javax.inject.Inject

class CreateCryptoUseCase @Inject constructor(
    private val cryptoRepository: ICryptoRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<CreateCryptoUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return cryptoRepository.createCrypto(params.crypto.toDto()).toResource(errorHandler)
    }

    class Params(val crypto: Crypto) : EmptyParams()
}