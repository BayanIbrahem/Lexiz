package com.dev.bayan.ibrahim.core.common.format

import com.dev.bayan.ibrahim.core.common.format.StringCapitalizer.*

private fun stringCapitalize(
    string: String,
    capitalizer: StringCapitalizer,
): String = when (capitalizer) {
    ALL_CAPS -> string.uppercase()
    NONE_CAPS -> string.lowercase()
    FIRST_CHAR -> string.lowercase().replaceFirstChar { it.uppercaseChar() }
    EACH_FIRST_CHAR -> {
        val builder = StringBuilder()

        var prevChar: Char? = null
        string.forEach { c ->
            if (prevChar?.isLetterOrDigit() == true) {
                builder.append(c.lowercaseChar())
            } else {
                builder.append(c.uppercaseChar())
            }
            prevChar = c
        }

        builder.toString()
    }
}

@JvmName("StringCapitalizeStringCapitalizer")
fun String.capitalize(capitalizer: StringCapitalizer): String = stringCapitalize(
    string = this,
    capitalizer = capitalizer
)

@JvmName("StringCapitalizerCCapitalizeString")
fun StringCapitalizer?.capitalize(string: String): String = if (this == null) {
    string
} else {
    stringCapitalize(string = string, capitalizer = this)
}

/**
 * equals to String.capitalize([StringCapitalizer.FIRST_CHAR])
 */
fun String.capitalizeFirst() = stringCapitalize(this, FIRST_CHAR)

/**
 * equals to String.capitalize([StringCapitalizer.EACH_FIRST_CHAR])
 */
fun String.capitalizeEachFirst() = stringCapitalize(this, EACH_FIRST_CHAR)

/**
 * Defines various string capitalization styles.
 *
 * @property ALL_CAPS Converts the entire string to uppercase.
 * - Example: "HELLO WORLD"
 * @property NONE_CAPS Converts the entire string to lowercase.
 * - Example: "hello world"
 * @property FIRST_CHAR Capitalizes only the first character of the string.
 * - Example: "Hello world"
 * @property EACH_FIRST_CHAR Capitalizes the first character of each word,
 * considering any non-alphanumeric character as a word boundary.
 * - Examples: "Hello, World!", "This-Is The 1st (Rank)."
 */
enum class StringCapitalizer {
    ALL_CAPS,
    NONE_CAPS,
    FIRST_CHAR,
    EACH_FIRST_CHAR,
}
