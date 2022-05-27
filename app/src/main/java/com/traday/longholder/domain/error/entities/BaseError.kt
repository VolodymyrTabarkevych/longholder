package com.traday.longholder.domain.error.entities

import androidx.annotation.StringRes

sealed class BaseError(@StringRes val stringResId: Int?) {

    class NetworkConnectionError(stringResId: Int) : BaseError(stringResId)

    class SomethingWentWrongError(stringResId: Int) : BaseError(stringResId)

    class UnauthorizedError(stringResId: Int?) : BaseError(stringResId)

    class UserAlreadyExistsError(stringResId: Int) : BaseError(stringResId)

    class NoUserAssociatedWithEmailError(stringResId: Int) : BaseError(stringResId)
}