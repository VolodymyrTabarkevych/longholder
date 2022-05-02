package com.traday.longholder.data.mapper

import com.traday.longholder.data.base.Result
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.extensions.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val RESOURCE_MAPPING_TAG = "RESOURCE_MAPPING"

fun <R : Any> Result<R>.toResource(errorHandler: IErrorHandler) = when (this) {
    is Result.Success -> Resource.Success(this.data)
    is Result.Error -> {
        loge(RESOURCE_MAPPING_TAG, error.message, error)
        Resource.Error(errorHandler.getError(error))
    }
}

inline fun <R : Any, T : Any> Result<R>.toResource(
    errorHandler: IErrorHandler,
    dataMapper: (R) -> T
) = when (this) {
    is Result.Success -> Resource.Success(dataMapper.invoke(this.data))
    is Result.Error -> {
        loge(RESOURCE_MAPPING_TAG, error.message, error)
        Resource.Error(errorHandler.getError(error))
    }
}

fun <R : Any> Flow<Result<R>>.toResource(errorHandler: IErrorHandler) = map {
    when (it) {
        is Result.Success -> Resource.Success(it.data)
        is Result.Error -> {
            loge(RESOURCE_MAPPING_TAG, it.error.message, it.error)
            Resource.Error(errorHandler.getError(it.error))
        }
    }
}

fun <R : Any, T : Any> Flow<Result<R>>.toResource(
    errorHandler: IErrorHandler,
    dataMapper: (R) -> T
) = map {
    when (it) {
        is Result.Success -> Resource.Success(dataMapper.invoke(it.data))
        is Result.Error -> {
            loge(RESOURCE_MAPPING_TAG, it.error.message, it.error)
            Resource.Error(errorHandler.getError(it.error))
        }
    }
}