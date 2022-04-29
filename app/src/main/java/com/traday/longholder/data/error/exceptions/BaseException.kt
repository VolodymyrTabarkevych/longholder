package com.traday.longholder.data.error.exceptions

import com.traday.longholder.remote.responsebody.base.ErrorResponse
import java.io.IOException

object BaseException {

    class NetworkConnectionException : IOException()

    class NoResponseContentException : IOException()

    open class NetworkRequestException(val error: ErrorResponse) : IOException()

    class SomethingWentWrongException(error: ErrorResponse) :
        NetworkRequestException(error)

    class IncorrectTokenException(error: ErrorResponse) :
        NetworkRequestException(error)

    class UserAlreadyExistsException(error: ErrorResponse) :
        NetworkRequestException(error)

    open class LocalRequestException : IOException()

    class NoDataException : LocalRequestException()
}