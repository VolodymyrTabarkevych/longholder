package com.traday.longholder.extensions

private const val DOT = '.'
private const val COMMA = ','

fun Double.replaceDotWithComma(): String {
    return this.toString().replace(DOT, COMMA)
}

fun String.replaceDotWithComma(): String {
    return this.replace(DOT, COMMA)
}

