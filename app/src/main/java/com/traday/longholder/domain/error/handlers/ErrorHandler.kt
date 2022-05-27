package com.traday.longholder.domain.error.handlers

import com.traday.longholder.R
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.domain.error.entities.BaseError
import javax.inject.Inject

class ErrorHandler @Inject constructor() : IErrorHandler {

    override fun getError(tr: Throwable): BaseError =
        when (tr) {
            is BaseException.NetworkConnectionException -> BaseError.NetworkConnectionError(R.string.error_no_internet_connection)
            is BaseException.UnauthorizedException -> BaseError.UnauthorizedError(null)
            is BaseException.UserAlreadyExistsException -> BaseError.UserAlreadyExistsError(R.string.error_user_already_exists)
            is BaseException.NoUserAssociatedWithEmailException -> BaseError.NoUserAssociatedWithEmailError(
                R.string.error_no_user_associated_with_email_exception
            )
            else -> BaseError.SomethingWentWrongError(R.string.error_unknown)
        }
}