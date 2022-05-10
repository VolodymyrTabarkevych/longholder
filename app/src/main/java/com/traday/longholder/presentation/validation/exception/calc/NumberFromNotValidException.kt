package com.traday.longholder.presentation.validation.exception.calc

import androidx.annotation.StringRes
import com.traday.longholder.presentation.validation.exception.base.ValidationException

class NumberFromNotValidException constructor(@StringRes val stringId: Int) : ValidationException()