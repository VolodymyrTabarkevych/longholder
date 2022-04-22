package com.traday.longholder.presentation.validation.validator.base

import com.traday.longholder.presentation.validation.exception.base.ValidationException

sealed class ValidateResult {

    object Success : ValidateResult()

    data class Error(val exception: ValidationException) : ValidateResult()
}