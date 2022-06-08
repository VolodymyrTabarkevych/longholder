package com.traday.longholder.domain.error.entities

import com.traday.longholder.domain.base.ProgressiveString

sealed class BaseError(val message: ProgressiveString) {

    class NetworkConnectionError(message: ProgressiveString) : BaseError(message)

    class SomethingWentWrongError(message: ProgressiveString) : BaseError(message)

    class UnauthorizedError(message: ProgressiveString) : BaseError(message)

    class UserAlreadyExistsError(message: ProgressiveString) : BaseError(message)

    class NoUserAssociatedWithEmailError(message: ProgressiveString) : BaseError(message)
}