package com.traday.longholder.domain.error.entities

sealed class BaseError(val msg: String) {

    class NetworkConnectionError(msg: String) : BaseError(msg)

    class SomethingWentWrongError(msg: String) : BaseError(msg)

    class UnauthorizedError(msg: String) : BaseError(msg)

    class UserAlreadyExistsError(msg: String) : BaseError(msg)
}