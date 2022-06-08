package com.traday.longholder.domain.error.handlers

import com.traday.longholder.R
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.domain.base.ProgressiveString
import com.traday.longholder.domain.error.entities.BaseError
import com.traday.longholder.utils.EMPTY_STRING
import javax.inject.Inject

class ErrorHandler @Inject constructor() : IErrorHandler {

    override fun getError(tr: Throwable): BaseError =
        when (tr) {
            is BaseException.NetworkConnectionException -> BaseError.NetworkConnectionError(
                ProgressiveString.Resource(R.string.error_no_internet_connection)
            )
            is BaseException.UnauthorizedException -> BaseError.UnauthorizedError(
                ProgressiveString.Default(
                    EMPTY_STRING
                )
            )
            is BaseException.UserAlreadyExistsException -> BaseError.UserAlreadyExistsError(
                ProgressiveString.Resource(R.string.error_user_already_exists)
            )
            is BaseException.NoUserAssociatedWithEmailException -> BaseError.NoUserAssociatedWithEmailError(
                ProgressiveString.Resource(R.string.error_no_user_associated_with_email_exception)
            )
            is BaseException.NetworkRequestException -> BaseError.SomethingWentWrongError(
                ProgressiveString.Default(tr.error.message)
            )
            else -> BaseError.SomethingWentWrongError(
                tr.localizedMessage?.let { message ->
                    ProgressiveString.Default(
                        message
                    )
                } ?: ProgressiveString.Resource(R.string.error_unknown)
            )
        }
}