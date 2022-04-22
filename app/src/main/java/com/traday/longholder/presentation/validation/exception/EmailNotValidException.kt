package com.traday.longholder.presentation.validation.exception

import androidx.annotation.StringRes
import com.traday.longholder.presentation.validation.exception.base.ValidationException

class EmailNotValidException constructor(@StringRes val stringId: Int) : ValidationException()