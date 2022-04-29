package com.traday.longholder.extensions

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.error.converteres.ExceptionConvertor
import com.traday.longholder.data.error.converteres.IExceptionConvertor
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.remote.datasource.base.BaseRemoteDataSource
import retrofit2.Response

private const val REMOTE_DATA_SOURCE = "REMOTE_DATA_SOURCE"
private const val LOCAL_DATA_SOURCE = "LOCAL_DATA_SOURCE"

suspend fun <T, S : Any> BaseRemoteDataSource<T>.safeApiCall(
    converter: IExceptionConvertor = ExceptionConvertor(),
    call: suspend () -> Response<S>?
): Result<S> =
    try {
        Result.Success(call()!!.body()!!)
    } catch (e: Exception) {
        if (e is BaseException.NetworkRequestException) {
            loge(REMOTE_DATA_SOURCE, e.error.message, e)
        }
        Result.Error(converter.getException(e))
    }

suspend fun <T : Any> BaseLocalDataSource.safeOperation(
    operation: suspend () -> T?
): Result<T> =
    try {
        val result = operation() ?: throw BaseException.NoDataException()
        Result.Success(result)
    } catch (e: Exception) {
        loge(LOCAL_DATA_SOURCE, e.message, e)
        Result.Error(e)
    }
