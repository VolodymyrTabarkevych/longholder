package com.traday.longholder.domain.error.handlers

import android.content.Context
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.domain.error.entities.BaseError
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorHandler @Inject constructor(@ApplicationContext context: Context) : IErrorHandler {

    private val rs by lazy { context.resources }

    override fun getError(tr: Throwable): BaseError =
        when (tr) {
            is BaseException.NetworkConnectionException -> BaseError.NetworkConnectionError(
                "Network error"
            )
            is BaseException.IncorrectTokenException -> BaseError.IncorrectTokenError("Incorrect token")
            is BaseException.UserAlreadyExistsException -> BaseError.UserAlreadyExistsError("User already exists")
            else -> BaseError.SomethingWentWrongError("Something wrong")
        }

    companion object {

        private const val EMPTY_STRING = ""
    }
}