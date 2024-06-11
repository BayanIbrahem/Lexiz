package com.dev.bayan.ibrahim.core.ui.utils

import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorResult
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString

fun RegexValidatorResult.inputRes(): Int = when (this) {
    RegexValidatorResult.NULL -> R.string.input_null
    RegexValidatorResult.INVALID -> R.string.input_invalid
    RegexValidatorResult.MATCH -> R.string.input_match
    RegexValidatorResult.SHORT -> R.string.input_short
    RegexValidatorResult.LONG -> R.string.input_long
    RegexValidatorResult.NO_CHAR -> R.string.input_no_char
    RegexValidatorResult.NO_LOWERCASE -> R.string.input_no_lowercase
    RegexValidatorResult.NO_UPPERCASE -> R.string.input_no_uppercase
    RegexValidatorResult.NO_DIGIT -> R.string.input_no_digit
    RegexValidatorResult.NO_SPECIAL -> R.string.input_no_special
    RegexValidatorResult.NO_DASH -> R.string.input_no_dash
    RegexValidatorResult.NO_UNDERSCORE -> R.string.input_no_underscore
    RegexValidatorResult.NO_DOT -> R.string.input_no_dot
    RegexValidatorResult.START_NO_CHAR -> R.string.input_start_no_char
    RegexValidatorResult.START_NO_LOWERCASE -> R.string.input_start_no_lowercase
    RegexValidatorResult.START_NO_UPPERCASE -> R.string.input_start_no_uppercase
    RegexValidatorResult.START_NO_DIGIT -> R.string.input_start_no_digit
    RegexValidatorResult.START_NO_SPECIAL -> R.string.input_start_no_special
    RegexValidatorResult.START_NO_DASH -> R.string.input_start_no_dash
    RegexValidatorResult.START_NO_UNDERSCORE -> R.string.input_start_no_underscore
    RegexValidatorResult.START_NO_DOT -> R.string.input_start_no_dot
    RegexValidatorResult.END_NO_CHAR -> R.string.input_end_no_char
    RegexValidatorResult.END_NO_LOWERCASE -> R.string.input_end_no_lowercase
    RegexValidatorResult.END_NO_UPPERCASE -> R.string.input_end_no_uppercase
    RegexValidatorResult.END_NO_DIGIT -> R.string.input_end_no_digit
    RegexValidatorResult.END_NO_SPECIAL -> R.string.input_end_no_special
    RegexValidatorResult.END_NO_DASH -> R.string.input_end_no_dash
    RegexValidatorResult.END_NO_UNDERSCORE -> R.string.input_end_no_underscore
    RegexValidatorResult.END_NO_DOT -> R.string.input_end_no_dot
    RegexValidatorResult.INVALID_CHAR -> R.string.input_invalid_char
    RegexValidatorResult.INVALID_LOWERCASE -> R.string.input_invalid_lowercase
    RegexValidatorResult.INVALID_UPPERCASE -> R.string.input_invalid_uppercase
    RegexValidatorResult.INVALID_DIGIT -> R.string.input_invalid_digit
    RegexValidatorResult.INVALID_SPECIAL -> R.string.input_invalid_special
    RegexValidatorResult.INVALID_DASH -> R.string.input_invalid_dash
    RegexValidatorResult.INVALID_UNDERSCORE -> R.string.input_invalid_underscore
    RegexValidatorResult.INVALID_DOT -> R.string.input_invalid_dot
}

fun RegexValidatorResult.inputDynamicString(
    input: String,
    invalidChar: String? = null,
): JaArDynamicString.StrRes {
    return JaArDynamicString.StrRes(
        inputRes(),
        listOf(input, invalidChar ?: "")
    )
}