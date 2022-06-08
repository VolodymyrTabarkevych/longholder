package com.traday.longholder.data.mapper

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.error.converteres.ExceptionConvertor
import com.traday.longholder.data.error.converteres.IExceptionConvertor
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.extensions.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.util.concurrent.CancellationException

private const val RESULT_MAPPER = "RESULT_MAPPING"

suspend fun <S : Any> apiResult(
    converter: IExceptionConvertor = ExceptionConvertor(),
    call: suspend () -> Response<S>?
): Result<S> =
    try {
        Result.Success(call()!!.body()!!)
    } catch (e: Exception) {
        when (e) {
            is CancellationException -> throw e
            is BaseException.NetworkRequestException -> loge(RESULT_MAPPER, e.error.message, e)
            else -> loge(RESULT_MAPPER, e.message, e)
        }
        Result.Error(converter.getException(e))
    }

suspend fun <S : Any> result(
    converter: IExceptionConvertor = ExceptionConvertor(),
    operation: suspend () -> S?
): Result<S> =
    try {
        Result.Success(operation()!!)
    } catch (e: Exception) {
        when (e) {
            is CancellationException -> throw e
            is BaseException.NetworkRequestException -> loge(RESULT_MAPPER, e.error.message, e)
            else -> loge(RESULT_MAPPER, e.message, e)
        }
        Result.Error(converter.getException(e))
    }

fun <T : Any> flowResult(
    converter: IExceptionConvertor = ExceptionConvertor(),
    operation: () -> Flow<T?>
): Flow<Result<T>> = operation.invoke()
    .map { value ->
        Result.Success(value!!)
    }
    .catch { e ->
        when (e) {
            is BaseException.NetworkRequestException -> loge(RESULT_MAPPER, e.error.message, e)
            is java.lang.Exception -> loge(RESULT_MAPPER, e.message, e)
            else -> loge(RESULT_MAPPER, e.message.toString())
        }
        Result.Error(converter.getException(Exception(e)))
    }


