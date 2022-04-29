package com.traday.longholder.remote.responsebody.base

data class ErrorResponse(
    val code: Int,
    val message: String?
)