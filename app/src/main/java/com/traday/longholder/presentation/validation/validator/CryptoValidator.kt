package com.traday.longholder.presentation.validation.validator

import com.traday.longholder.R
import com.traday.longholder.presentation.validation.exception.CryptoValueNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.presentation.validation.validator.base.Validator

sealed class CryptoValidator<T> : Validator<T> {

    object Amount : CryptoValidator<String>() {

        override fun validate(value: String): ValidateResult {
            return try {
                val parsedValue = value.toDouble()
                if (parsedValue < MIN_AMOUNT) return ValidateResult.Error(
                    CryptoValueNotValidException(R.string.validation_crypto_amount_not_valid)
                )
                ValidateResult.Success
            } catch (e: Exception) {
                return ValidateResult.Error(CryptoValueNotValidException(R.string.validation_crypto_amount_not_valid))
            }
        }
    }

    companion object {

        private const val MIN_AMOUNT = 1.0
    }
}