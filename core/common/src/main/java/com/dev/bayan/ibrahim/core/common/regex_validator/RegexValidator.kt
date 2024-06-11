package com.dev.bayan.ibrahim.core.common.regex_validator

import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.CHAR
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.DASH
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.DIGIT
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.DOT
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.LOWERCASE
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.SPECIAL
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.UNDERSCORE
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.UPPERCASE
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorCharType.entries

/**
 * check if specific string matches some valid using regex see [RegexValidator] and return a result
 * see [RegexValidatorResult] with the index of char for some results
 * every thing except return non null "firstWrongCharIndex"
 * - [RegexValidatorResult.NULL]
 * - [RegexValidatorResult.SHORT]
 * - [RegexValidatorResult.LONG]
 * - [RegexValidatorResult.MATCH]
 * - NO_* like [RegexValidatorResult.NO_CHAR]
 *
 * @see RegexValidator
 * @see RegexValidatorResult
 */
fun String?.checkIfValid(validator: RegexValidator): Pair<RegexValidatorResult, Int?> {
    this ?: return RegexValidatorResult.NULL to null
    checkIfInValidChars(validator)?.let { return it }
    checkIfValidLength(validator)?.let { return it }
    val allowedPattern = validator.buildAllowedRegex()
    val onlyAllowedCharacter = allowedPattern?.let { this.matches(it) } ?: true
    if (onlyAllowedCharacter) {
        // check required chars
        checkContainsAllRequired(validator.requiredCharsTypes)?.let { return it }
        // check starts with:
        checkStartsWith(validator.startsWith)?.let { return it }
        // check ends with:
        checkEndsWith(validator.endsWith)?.let { return it }
        // matched
        return RegexValidatorResult.MATCH to null
    } else {
        val firstInvalidCharIndex: Int? = getFirstInvalidCharIndex(validator)
        val firstInvalidChar = this@checkIfValid.getOrNull(firstInvalidCharIndex ?: -1)
        firstInvalidChar.getCharType(firstInvalidCharIndex)?.let { return it }
        return RegexValidatorResult.INVALID_SPECIAL to firstInvalidCharIndex
    }
}

private fun String.checkIfInValidChars(validator: RegexValidator): Pair<RegexValidatorResult, Int?>? {
    return validator.validChars?.let { set ->
        val index = (if (validator.ignoreCase) lowercase() else this).indexOfFirst {
            it !in set && !it.isWhitespace()
        }
        if (index < 0) {
            return null
        } else {
            RegexValidatorResult.INVALID to index
        }
    }
}

private fun String.checkIfValidLength(validator: RegexValidator): Pair<RegexValidatorResult, Int?>? {
    return length.run {
        // out of range error return no char index
        if (this < validator.minLength) {
            RegexValidatorResult.SHORT to null
        } else if (this > validator.maxLength) {
            RegexValidatorResult.LONG to null
        } else null
    }
}

private fun RegexValidator.buildAllowedRegex(): Regex? {
    return allowedCharsTypes?.joinToString(
        separator = "|",
        prefix = "^(?:",
        postfix = ")+$"
    ) { it.regexPattern }?.toRegex()
}

private fun RegexValidator.buildInvalidRegex(): Regex? {
    return allowedCharsTypes?.joinToString(
        separator = "|",
        prefix = "(?!(",
        postfix = "))"
    ) { it.regexPattern }?.toRegex()
}

private fun String.checkContainsAllRequired(required: List<RegexValidatorCharType>): Pair<RegexValidatorResult, Int?>? {
    required.forEach {
        if (this.contains(it.regexPattern.toRegex()).not()) {
            return when (it) {
                CHAR -> RegexValidatorResult.NO_CHAR
                LOWERCASE -> RegexValidatorResult.NO_LOWERCASE
                UPPERCASE -> RegexValidatorResult.NO_UPPERCASE
                DIGIT -> RegexValidatorResult.NO_DIGIT
                SPECIAL -> RegexValidatorResult.NO_SPECIAL
                DASH -> RegexValidatorResult.NO_DASH
                UNDERSCORE -> RegexValidatorResult.NO_UNDERSCORE
                DOT -> RegexValidatorResult.NO_DOT
            } to null
        }
    }
    return null
}

private fun String.checkStartsWith(startsWith: RegexValidatorCharType?): Pair<RegexValidatorResult, Int?>? {
    startsWith ?: return null
    val startWithRegex = startsWith.regexPattern.run { "^${startsWith.regexPattern}.*".toRegex() }
    if (!this.matches(startWithRegex)) {
        return when (startsWith) {
            CHAR -> RegexValidatorResult.START_NO_CHAR
            LOWERCASE -> RegexValidatorResult.START_NO_LOWERCASE
            UPPERCASE -> RegexValidatorResult.START_NO_UPPERCASE
            DIGIT -> RegexValidatorResult.START_NO_DIGIT
            SPECIAL -> RegexValidatorResult.START_NO_SPECIAL
            DASH -> RegexValidatorResult.START_NO_DASH
            UNDERSCORE -> RegexValidatorResult.START_NO_UNDERSCORE
            DOT -> RegexValidatorResult.START_NO_DOT
        } to 0
    }
    return null
}

private fun String.checkEndsWith(endsWith: RegexValidatorCharType?): Pair<RegexValidatorResult, Int?>? {
    endsWith ?: return null
    val startWithRegex = endsWith.regexPattern.run { ".*${endsWith.regexPattern}$".toRegex() }
    if (!this.matches(startWithRegex)) {
        return when (endsWith) {
            CHAR -> RegexValidatorResult.END_NO_CHAR
            LOWERCASE -> RegexValidatorResult.END_NO_LOWERCASE
            UPPERCASE -> RegexValidatorResult.END_NO_UPPERCASE
            DIGIT -> RegexValidatorResult.END_NO_DIGIT
            SPECIAL -> RegexValidatorResult.END_NO_SPECIAL
            DASH -> RegexValidatorResult.END_NO_DASH
            UNDERSCORE -> RegexValidatorResult.END_NO_UNDERSCORE
            DOT -> RegexValidatorResult.END_NO_DOT
        } to this.length.dec()
    }
    return null
}

private fun String.getFirstInvalidCharIndex(validator: RegexValidator): Int? {
    val match = validator.buildInvalidRegex()?.find(this)
    return match?.range?.first.run {
        if (this != null && this !in indices) null else this
    }
}

private fun Char?.getCharType(index: Int?): Pair<RegexValidatorResult, Int?>? {
    entries.forEach {
        if (this?.toString()?.matches(it.regexPattern.toRegex()) == true) {
            return when (it) {
                CHAR -> RegexValidatorResult.INVALID_CHAR
                LOWERCASE -> RegexValidatorResult.INVALID_LOWERCASE
                UPPERCASE -> RegexValidatorResult.INVALID_UPPERCASE
                DIGIT -> RegexValidatorResult.INVALID_DIGIT
                SPECIAL -> RegexValidatorResult.INVALID_SPECIAL
                DASH -> RegexValidatorResult.INVALID_DASH
                UNDERSCORE -> RegexValidatorResult.INVALID_UNDERSCORE
                DOT -> RegexValidatorResult.INVALID_DOT
            } to index
        }
    }
    return null
}

sealed interface RegexValidator {
    val minLength: Int
    val maxLength: Int
    val allowedCharsTypes: List<RegexValidatorCharType>?
    val requiredCharsTypes: List<RegexValidatorCharType>
    val startsWith: RegexValidatorCharType?
    val endsWith: RegexValidatorCharType?
    val validChars: Set<Char>?
    val ignoreCase: Boolean

    data class OfValidChars(
        override val validChars: Set<Char>,
        override val ignoreCase: Boolean,
        override val minLength: Int = 0,
        override val maxLength: Int = 20,
        override val allowedCharsTypes: List<RegexValidatorCharType>? = null,
        override val requiredCharsTypes: List<RegexValidatorCharType> = listOf(),
        override val startsWith: RegexValidatorCharType? = null,
        override val endsWith: RegexValidatorCharType? = null,
    ) : RegexValidator
}

enum class RegexValidatorCharType(val regexPattern: String) {
    LOWERCASE("[a-z]"),
    UPPERCASE("[A-Z]"),
    CHAR("[a-zA-Z]"),
    DIGIT("[0-9]"),
    DASH("[-]"),
    DOT("[.]"),
    UNDERSCORE("[_]"),
    SPECIAL("[@#!\$%^&*]"),
}

