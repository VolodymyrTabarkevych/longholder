package com.traday.longholder.data.error.converteres

import com.traday.longholder.data.error.exceptions.BaseException
import javax.inject.Inject

class ExceptionConvertor @Inject constructor() : IExceptionConvertor {

    override fun getException(e: Exception): Exception = when (e) {
        is BaseException.NetworkRequestException -> {
            when (e.error.code) {
                UNAUTHORIZED_ERROR_CODE_401 -> BaseException.UnauthorizedException(e.error)
                else -> BaseException.SomethingWentWrongException(e.error)
            }
        }
        else -> e
    }

    companion object {

        const val BAD_REQUEST_ERROR_CODE_400 = 400
        const val UNAUTHORIZED_ERROR_CODE_401 = 401
        const val FORBIDDEN_ERROR_CODE_403 = 403
        const val NOT_FOUND_ERROR_CODE_404 = 404
        const val CONFLICT_ERROR_CODE_409 = 409
        const val GONE_ERROR_CODE_410 = 410
        const val INTERNAL_SERVER_ERROR_500 = 500
    }
}
