package com.traday.longholder.extensions

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.error.converteres.ExceptionConvertor
import com.traday.longholder.data.error.converteres.IExceptionConvertor
import com.traday.longholder.data.error.exceptions.BaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response

private const val RESULT_MAPPER = "RESULT_MAPPING"

suspend fun <S : Any> apiResult(
    converter: IExceptionConvertor = ExceptionConvertor(),
    call: suspend () -> Response<S>?
): Result<S> =
    try {
        Result.Success(call()!!.body()!!)
    } catch (e: Exception) {
        if (e is BaseException.NetworkRequestException) {
            loge(RESULT_MAPPER, e.error.message, e)
        }
        Result.Error(converter.getException(e))
    }

suspend fun <T : Any> result(
    operation: suspend () -> T?
): Result<T> =
    try {
        Result.Success(operation() ?: throw BaseException.NoDataException())
    } catch (e: Exception) {
        loge(RESULT_MAPPER, e.message, e)
        Result.Error(e)
    }

fun <T : Any> flowResult(
    converter: IExceptionConvertor = ExceptionConvertor(),
    operation: () -> Flow<T?>
): Flow<Result<T>> = operation.invoke()
    .map { value ->
        Result.Success(value ?: throw BaseException.NoDataException())
    }
    .catch { e ->
        if (e is BaseException.NetworkRequestException) {
            loge(RESULT_MAPPER, e.error.message, e)
        }
        Result.Error(converter.getException(Exception(e)))
    }


