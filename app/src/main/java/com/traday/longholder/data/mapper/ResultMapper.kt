package com.traday.longholder.data.mapper

import com.traday.longholder.data.base.Result
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler


fun <R : Any> Result<R>.toResource(errorHandler: IErrorHandler) = when (this) {
    is Result.Success -> Resource.Success(this.data)
    is Result.Error -> Resource.Error(errorHandler.getError(error))
}

inline fun <R : Any, T : Any> Result<R>.toResource(
    errorHandler: IErrorHandler,
    dataMapper: (R) -> T
) = when (this) {
    is Result.Success -> Resource.Success(dataMapper.invoke(this.data))
    is Result.Error -> Resource.Error(errorHandler.getError(error))
}