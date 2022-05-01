package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Crypto
import com.traday.longholder.domain.repository.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptosUseCase @Inject constructor(
    private val cryptoRepository: ICryptoRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<EmptyParams, List<Crypto>>() {

    override fun run(params: EmptyParams): Flow<Resource<List<Crypto>>> {
        return cryptoRepository.getCryptos()
            .toResource(errorHandler) { cryptos -> cryptos.map { it.toDomain() } }
    }
}