package com.traday.longholder.presentation.validation.validator

import com.traday.longholder.R
import com.traday.longholder.presentation.validation.exception.EmailNotValidException
import com.traday.longholder.presentation.validation.exception.NameNotValidException
import com.traday.longholder.presentation.validation.exception.PasswordMatchException
import com.traday.longholder.presentation.validation.exception.PasswordNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.presentation.validation.validator.base.Validator

sealed class CredentialValidator<T> : Validator<T>, CommonLoginValidator() {

    object Name : CredentialValidator<String?>() {

        override fun validate(value: String?): ValidateResult =
            if (!value.isNullOrBlank()) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(NameNotValidException(R.string.validation_name_not_valid))
            }
    }

    object Surename : CredentialValidator<String?>() {

        override fun validate(value: String?): ValidateResult =
            if (!value.isNullOrBlank()) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(NameNotValidException(R.string.validation_surename_not_valid))
            }
    }

    object Email : CredentialValidator<String?>() {

        override fun validate(value: String?): ValidateResult =
            if (!value.isNullOrBlank()) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(EmailNotValidException(R.string.validation_email_not_valid))
            }
/*            if (!value.isNullOrBlank() && validateEmail(value)) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(EmailNotValidException(R.string.validation_email_not_valid))
            }*/
    }

    object Password : CredentialValidator<String?>() {

        override fun validate(value: String?): ValidateResult =
            if (!value.isNullOrBlank() && validatePassword(value)) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(PasswordNotValidException(R.string.validation_password_not_valid))
            }
    }

    object PasswordMatch {

        fun validate(password: String, confirmPassword: String): ValidateResult =
            if (password == confirmPassword) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(PasswordMatchException(R.string.validation_password_match))
            }
    }
}