package com.traday.longholder.domain.base

import com.traday.longholder.domain.error.entities.BaseError

sealed class Resource<out T : Any> {

    object Loading : Resource<Nothing>()

    data class Success<out T : Any>(val data: T) : Resource<T>()

    data class Error(val error: BaseError) : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}