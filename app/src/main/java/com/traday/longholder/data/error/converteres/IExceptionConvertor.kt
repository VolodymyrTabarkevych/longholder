package com.traday.longholder.data.error.converteres


interface IExceptionConvertor {

    fun getException(e: Exception): Exception
}
