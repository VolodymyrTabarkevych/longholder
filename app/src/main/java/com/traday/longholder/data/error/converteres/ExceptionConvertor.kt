package com.traday.longholder.data.error.converteres

import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.data.remote.responsebody.base.ErrorResponse
import javax.inject.Inject

class ExceptionConvertor @Inject constructor() : IExceptionConvertor {

    override fun getException(e: Exception): Exception = when (e) {
        is BaseException.NetworkRequestException -> {
            when (e.error.code) {
                BAD_REQUEST_ERROR_CODE_400 -> handle400(e.error)
                UNAUTHORIZED_ERROR_CODE_401 -> handle401(e.error)
                INTERNAL_SERVER_ERROR_500 -> handle500(e.error)
                else -> BaseException.SomethingWentWrongException(e.error)
            }
        }
        else -> e
    }

    private fun handle400(error: ErrorResponse): Exception {
        return when (error.message) {
            NO_AVAILABLE_ACTIVES -> BaseException.NoAvailableActives(error)
            USER_ALREADY_EXISTS_ERROR -> BaseException.UserAlreadyExistsException(error)
            else -> BaseException.SomethingWentWrongException(error)
        }
    }

    private fun handle401(error: ErrorResponse): Exception {
        return if (error.message != WRONG_LOGIN_OR_PASSWORD) {
            BaseException.UnauthorizedException(error)
        } else {
            BaseException.SomethingWentWrongException(error)
        }
    }

    private fun handle500(error: ErrorResponse): Exception {
        return if (error.message == USER_ALREADY_EXISTS_ERROR) {
            BaseException.UserAlreadyExistsException(error)
        } else {
            BaseException.SomethingWentWrongException(error)
        }
    }

    companion object {

        const val BAD_REQUEST_ERROR_CODE_400 = 400
        const val UNAUTHORIZED_ERROR_CODE_401 = 401
        const val FORBIDDEN_ERROR_CODE_403 = 403
        const val NOT_FOUND_ERROR_CODE_404 = 404
        const val CONFLICT_ERROR_CODE_409 = 409
        const val GONE_ERROR_CODE_410 = 410
        const val INTERNAL_SERVER_ERROR_500 = 500

        const val WRONG_LOGIN_OR_PASSWORD = "Wrong login or password!"
        const val USER_ALREADY_EXISTS_ERROR = "User already exists!"
        const val NO_AVAILABLE_ACTIVES = "No available values!"
    }
}
