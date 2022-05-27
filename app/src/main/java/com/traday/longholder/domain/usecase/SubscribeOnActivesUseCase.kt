package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.repository.IActiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeOnActivesUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<SubscribeOnActivesUseCase.Params, List<Active>>() {

    override fun run(params: Params): Flow<Resource<List<Active>>> {
        return activeRepository.subscribeOnActives(params.syncAtStart)
            .toResource(errorHandler) { cryptos -> cryptos.map { it.toDomain() }.asReversed() }
    }

    class Params(val syncAtStart: Boolean) : EmptyParams()
}