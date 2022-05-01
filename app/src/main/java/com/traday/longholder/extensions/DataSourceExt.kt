package com.traday.longholder.extensions

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.error.converteres.ExceptionConvertor
import com.traday.longholder.data.error.converteres.IExceptionConvertor
import com.traday.longholder.data.error.exceptions.BaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response

private const val REMOTE_DATA_SOURCE = "REMOTE_DATA_SOURCE"
private const val LOCAL_DATA_SOURCE = "LOCAL_DATA_SOURCE"

suspend fun <S : Any> safeApiCall(
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

suspend fun <T : Any> safeOperation(
    operation: suspend () -> T?
): Result<T> =
    try {
        Result.Success(operation() ?: throw BaseException.NoDataException())
    } catch (e: Exception) {
        loge(LOCAL_DATA_SOURCE, e.message, e)
        Result.Error(e)
    }

fun <T : Any> safeFlow(
    converter: IExceptionConvertor = ExceptionConvertor(),
    operation: () -> Flow<T?>
): Flow<Result<T>> = operation.invoke()
    .map { value ->
        Result.Success(value ?: throw BaseException.NoDataException())
    }
    .catch { e ->
        if (e is BaseException.NetworkRequestException) {
            loge(REMOTE_DATA_SOURCE, e.error.message, e)
        }
        Result.Error(converter.getException(Exception(e)))
    }


