package com.dev.bayan.ibrahim.core.common.regex_validator

import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorResult.INVALID
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorResult.LONG
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorResult.MATCH
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorResult.NULL
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidatorResult.SHORT

/**
 * any possible result for string matching
 * [NULL], [MATCH], [INVALID], [SHORT], [LONG],
 * - {[NO_*]} means the string should contain a specific chars but it doesn't
 * - {[START_NO_*]} means the string should start with specific chars but it doesn't
 * - {[END_NO_*]} means the string should end with specific chars but it doesn't
 * - {[INVALID_*]} means the string should NOT contain a specific chars but it does
 * ____
 * - {[*_CHAR]} lowercase or upper case english character (regex of [a-zA-Z])
 * - {[*_LOWERCASE]} lowercase case english character (regex of [a-z])
 * - {[*_UPPERCASE]} upper case english character (regex of [A-Z])
 * - {[*_DIGIT]} digits (regex of [0-9])
 * - {[*_SPECIAL]} any special character
 * - {[*_DOT]} dot (regex of [.])
 */
enum class RegexValidatorResult {
    NULL, INVALID, MATCH, SHORT, LONG,

    NO_CHAR, NO_LOWERCASE,
    NO_UPPERCASE, NO_DIGIT,
    NO_SPECIAL, NO_DASH,
    NO_UNDERSCORE, NO_DOT,

    START_NO_CHAR, START_NO_LOWERCASE,
    START_NO_UPPERCASE, START_NO_DIGIT,
    START_NO_SPECIAL, START_NO_DASH,
    START_NO_UNDERSCORE, START_NO_DOT,

    END_NO_CHAR, END_NO_LOWERCASE,
    END_NO_UPPERCASE, END_NO_DIGIT,
    END_NO_SPECIAL, END_NO_DASH,
    END_NO_UNDERSCORE, END_NO_DOT,

    INVALID_CHAR, INVALID_LOWERCASE,
    INVALID_UPPERCASE, INVALID_DIGIT,
    INVALID_SPECIAL, INVALID_DASH,
    INVALID_UNDERSCORE, INVALID_DOT;

    val matched: Boolean
        get() = this == MATCH
//    val isNotInRange: Boolean
//        get() = this == SHORT || this == LONG
//    val isNoStart: Boolean
//        get() = this == START_NO_CHAR || this == START_NO_LOWERCASE || this == START_NO_UPPERCASE
//                || this == START_NO_DIGIT || this == START_NO_SPECIAL || this == START_NO_DASH
//                || this == START_NO_UNDERSCORE || this == START_NO_DOT
//
//
//    val isNoEnd: Boolean
//        get() = this == END_NO_CHAR || this == END_NO_LOWERCASE || this == END_NO_UPPERCASE
//                || this == END_NO_DIGIT || this == END_NO_SPECIAL || this == END_NO_DASH
//                || this == END_NO_UNDERSCORE || this == END_NO_DOT
//
//    val isInvalid: Boolean
//        get() = this == INVALID_CHAR || this == INVALID_LOWERCASE || this == INVALID_UPPERCASE
//                || this == INVALID_DIGIT || this == INVALID_SPECIAL || this == INVALID_DASH
//                || this == INVALID_UNDERSCORE || this == INVALID_DOT

}
