package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDto
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.repository.IActiveRepository
import javax.inject.Inject

class CreateActiveUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<CreateActiveUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return activeRepository.createActive(params.active.toDto()).toResource(errorHandler)
    }

    class Params(val active: Active) : EmptyParams()
}