package com.traday.longholder.domain.error.handlers

import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.domain.error.entities.BaseError
import javax.inject.Inject

class ErrorHandler @Inject constructor() : IErrorHandler {

    override fun getError(tr: Throwable): BaseError =
        when (tr) {
            is BaseException.NetworkConnectionException -> BaseError.NetworkConnectionError(
                ERROR_MESSAGE_NO_INTERNET_CONNECTION
            )
            is BaseException.UnauthorizedException -> BaseError.UnauthorizedError(tr.error.message)
            is BaseException.UserAlreadyExistsException -> BaseError.UserAlreadyExistsError(tr.error.message)
            else -> BaseError.SomethingWentWrongError(ERROR_MESSAGE_UNKNOWN)
        }

    companion object {

        private const val ERROR_MESSAGE_NO_INTERNET_CONNECTION = "No internet connection"
        private const val ERROR_MESSAGE_UNKNOWN = "Internal Server Error, please try again later"
    }
}