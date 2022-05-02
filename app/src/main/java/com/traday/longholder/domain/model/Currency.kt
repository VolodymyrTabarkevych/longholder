package com.traday.longholder.domain.model

import com.traday.longholder.utils.EMPTY_STRING

data class Currency(
    val id: Int,
    val linkToPhoto: String?,
    val name: String?,
    val indexOnExchange: String,
    val price: Double,
    val dateOfUpdate: String
) {

    override fun toString(): String {
        return name ?: EMPTY_STRING
    }
}