package com.traday.longholder.presentation.validation.validator

import com.traday.longholder.R
import com.traday.longholder.presentation.validation.exception.CalendarDateNotValidException
import com.traday.longholder.presentation.validation.exception.CalendarSameDateException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.presentation.validation.validator.base.Validator
import com.traday.longholder.utils.CALENDAR_FORMAT_PATTERN

sealed class CalendarValidator<T> : Validator<T> {

    object Date : CalendarValidator<String>() {

        override fun validate(value: String): ValidateResult {
            return if (value.isNotBlank() && value != CALENDAR_FORMAT_PATTERN && value.any { it.isDigit() }) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(CalendarDateNotValidException(0))
            }
        }
    }

    object DatesNotSame {

        fun validate(oldValue: String, newValue: String): ValidateResult =
            if (oldValue == newValue) {
                ValidateResult.Error(CalendarSameDateException(R.string.validation_same_date))
            } else {
                ValidateResult.Success
            }
    }
}