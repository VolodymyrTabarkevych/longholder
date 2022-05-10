package com.traday.longholder.presentation.validation.validator

import com.traday.longholder.R
import com.traday.longholder.presentation.validation.exception.calc.*
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.presentation.validation.validator.base.Validator

sealed class CalcValidation<T> : Validator<T> {

    object FromPercent : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(FromPercentNotValidException(R.string.validation_value_not_valid))
            }
    }

    object FromPercentNumber : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(FromPercentNumberNotValidException(R.string.validation_value_not_valid))
            }
    }

    object Number : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(NumberNotValidException(R.string.validation_value_not_valid))
            }
    }

    object NumberFrom : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(NumberFromNotValidException(R.string.validation_value_not_valid))
            }
    }

    object AddPercent : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(AddPercentNotValidException(R.string.validation_value_not_valid))
            }
    }

    object AddPercentNumber : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(AddPercentNumberNotValidException(R.string.validation_value_not_valid))
            }
    }

    object SubtractPercent : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(SubtractPercentNotValidException(R.string.validation_value_not_valid))
            }
    }

    object SubtractPercentNumber : CalcValidation<Double?>() {

        override fun validate(value: Double?): ValidateResult =
            if (value != null) {
                ValidateResult.Success
            } else {
                ValidateResult.Error(SubtractPercentNumberNotValidException(R.string.validation_value_not_valid))
            }
    }
}