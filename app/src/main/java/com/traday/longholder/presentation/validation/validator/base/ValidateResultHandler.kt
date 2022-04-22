package com.traday.longholder.presentation.validation.validator.base

interface ValidateResultHandler {

    fun onValidateFields(onValidateSuccess: () -> Unit, vararg validateResult: ValidateResult?)

    fun onValidateError(errorList: List<ValidateResult.Error>)
}