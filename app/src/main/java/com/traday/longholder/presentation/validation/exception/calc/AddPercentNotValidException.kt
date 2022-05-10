package com.traday.longholder.presentation.validation.exception.calc

import androidx.annotation.StringRes
import com.traday.longholder.presentation.validation.exception.base.ValidationException

class AddPercentNotValidException constructor(@StringRes val stringId: Int) : ValidationException()