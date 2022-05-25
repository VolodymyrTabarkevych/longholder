package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.repository.IActiveRepository
import javax.inject.Inject

class UpdateActiveUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<UpdateActiveUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return activeRepository.updateActive(params.active).toResource(errorHandler)
    }

    class Params(val active: Active) : EmptyParams()
}