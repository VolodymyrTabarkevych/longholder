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

class GetActivesUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<EmptyParams, List<Active>>() {

    override fun run(params: EmptyParams): Flow<Resource<List<Active>>> {
        return activeRepository.getActives()
            .toResource(errorHandler) { cryptos -> cryptos.map { it.toDomain() } }
    }
}