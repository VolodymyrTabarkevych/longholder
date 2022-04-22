package com.traday.longholder.presentation.validation.validator.base

interface Validator<T> {

    fun validate(value: T): ValidateResult
}