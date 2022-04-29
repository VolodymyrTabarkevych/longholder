package com.traday.longholder.presentation.validation.validator

import com.traday.longholder.R
import com.traday.longholder.presentation.validation.exception.PasswordNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.presentation.validation.validator.base.Validator

sealed class CredentialValidator<T> : Validator<T>, CommonLoginValidator() {

    object Email : CredentialValidator<String?>() {

        override fun validate(value: String?): ValidateResult =
            if (!value.isNullOrBlank() && validateEmail(value)) {
                ValidateResult.Success
            } else {
                ValidateResult.Success
                //todo enable when needed
                //ValidateResult.Error(EmailNotValidException(R.string.validation_email_not_valid))
            }
    }

    object Password : CredentialValidator<String?>() {

        override fun validate(value: String?): ValidateResult =
            if (!value.isNullOrBlank() && validatePassword(value)) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(PasswordNotValidException(R.string.validation_password_not_valid))
            }
    }
}