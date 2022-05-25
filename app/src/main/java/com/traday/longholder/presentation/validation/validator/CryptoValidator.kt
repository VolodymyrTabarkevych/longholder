package com.traday.longholder.presentation.validation.validator

import com.traday.longholder.R
import com.traday.longholder.presentation.validation.exception.CryptoSameAmountException
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

    object AmountsNotSame {

        fun validate(oldValue: String, newValue: String): ValidateResult =
            if (oldValue.toDoubleOrNull() == newValue.toDoubleOrNull()) {
                ValidateResult.Error(CryptoSameAmountException(R.string.validation_same_value))
            } else {
                ValidateResult.Success
            }
    }

    companion object {

        private const val MIN_AMOUNT = 1.0
    }
}