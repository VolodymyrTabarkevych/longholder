package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.IActiveRepository
import javax.inject.Inject

class DeleteActiveUseCase @Inject constructor(
    private val activeRepository: IActiveRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<DeleteActiveUseCase.Params, Unit>() {

    override suspend fun run(params: Params): Resource<Unit> {
        return activeRepository.deleteActive(params.id).toResource(errorHandler)
    }

    class Params(val id: Int) : EmptyParams()
}