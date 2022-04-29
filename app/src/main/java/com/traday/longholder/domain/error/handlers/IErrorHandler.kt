package com.traday.longholder.domain.error.handlers

import com.traday.longholder.domain.error.entities.BaseError

interface IErrorHandler {

    fun getError(tr: Throwable): BaseError
}