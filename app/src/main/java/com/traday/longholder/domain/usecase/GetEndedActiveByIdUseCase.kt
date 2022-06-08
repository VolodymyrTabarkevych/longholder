package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.repository.IActiveRepository
import javax.inject.Inject

class GetEndedActiveByIdUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<GetEndedActiveByIdUseCase.Params, Active>() {

    override suspend fun run(params: Params): Resource<Active> {
        return activeRepository.getEndedActiveById(params.id)
            .toResource(errorHandler) { it.toDomain() }
    }

    class Params(val id: Int) : EmptyParams()
}