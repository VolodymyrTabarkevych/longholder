package com.traday.longholder.presentation.validation.validator

import android.util.Patterns
import java.util.regex.Pattern

open class CommonLoginValidator {

    fun validateEmail(email: String) = email.matches(Patterns.EMAIL_ADDRESS.toRegex())

    fun validatePassword(password: String): Boolean {
        val validCharactersPattern = Pattern.compile("^[a-zA-Z0-9$SPECIAL_SYMBOLS]+$")
        val specialCharPatten = Pattern.compile("[$SPECIAL_SYMBOLS]")
        val upperCasePatten = Pattern.compile("[A-Z]")
        val lowerCasePatten = Pattern.compile("[a-z]")
        val digitPatten = Pattern.compile("[0-9]")


        val isLengthValid = password.length >= VALID_PASSWORD_LENGTH
        val isHaveOnlyValidCharacters = validCharactersPattern.matcher(password).matches()
        val isHaveOneSpecial = specialCharPatten.matcher(password).find()
        val isHaveOneUppercase = upperCasePatten.matcher(password).find()
        val isHaveOneLowercase = lowerCasePatten.matcher(password).find()
        val isHaveOneDigit = digitPatten.matcher(password).find()
        return isLengthValid && isHaveOnlyValidCharacters && isHaveOneSpecial && isHaveOneUppercase && isHaveOneLowercase && isHaveOneDigit
    }

    companion object {

        private const val VALID_PASSWORD_LENGTH = 8
        private const val SPECIAL_SYMBOLS = "-+_~!@#$%^&*?\\\\/().:;\\\"'\\[\\]{}|,"
    }
}