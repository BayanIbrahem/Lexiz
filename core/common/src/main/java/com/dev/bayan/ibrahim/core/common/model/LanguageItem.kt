package com.dev.bayan.ibrahim.core.common.model

import java.lang.StringBuilder

data class LanguageItem(
    override val languageCode: String,
    val localDisplayName: String,
    val selfDisplayName: String,
) : ModelItem {
    override val value = localDisplayName
    override val subtitle: String = selfDisplayName
}

data class EditLanguageItem(
    override val languageCode: String,
    val localDisplayName: String,
    val selfDisplayName: String,
    val interchangeables: LanguageInterchangeables,
    val validChars: Set<Char>,
    val ignorables: Set<Char>,
) : ModelItem {
    override val value = localDisplayName
    override val subtitle: String = selfDisplayName
}

fun EditLanguageItem.asLanguageItem(): LanguageItem = LanguageItem(
    languageCode = languageCode,
    localDisplayName = localDisplayName,
    selfDisplayName = selfDisplayName
)


fun interchangeablesOf(vararg groups: List<Char>) = LanguageInterchangeables(groups.asList())
class LanguageInterchangeables(private val groups: List<List<Char>>) {
    private val entries: Map<Char, Int> = groups.mapIndexed { index, chars ->
        chars.map { it to index }
    }.flatten().toMap()

    private val firsts: Map<Int, Char> = groups.mapIndexedNotNull() { index, chars ->
        chars.firstOrNull()?.run { index to this }
    }.toMap()

    fun isEmpty(): Boolean = groups.all { it.isEmpty() }

    operator fun get(key: Char): Int? = entries[key]
    fun containsValue(value: Int): Boolean = entries.containsValue(value)
    fun containsKey(key: Char): Boolean = entries.containsKey(key)

    /**
     * returns false if none of characters is between entities
     */
    fun interchangeables(char1: Char, char2: Char): Boolean {
        val group1 = entries[char1] ?: false
        val group2 = entries[char2] ?: false
        return group1 == group2
    }

    fun firstOfGroup(char: Char, keepUppercase: Boolean = false): Char {
        val first = entries[char]?.run { firsts[this] } ?: char

        return if (keepUppercase && char.isUpperCase()) {
            first.uppercaseChar()
        } else first
    }

    // return true if the two strings are interchangeables
    fun interchangeables(
        string1: String,
        string2: String,
        ignoreCase: Boolean = true,
    ): Boolean {
        if (ignoreCase) return interchangeables(string1.lowercase(), string2.lowercase(), false)
        if (string1.length != string2.length) return false
        repeat(string1.length) {
            val iCharsInterchangeables = interchangeables(string1[it], string2[it])
            if (!iCharsInterchangeables) return false
        }
        return true
    }

    /**
     * @return each char of the new string is the first char of the old string group
     */
    fun mapToFirstOfGroup(string: String, keepUppercase: Boolean = false): String {
        return StringBuilder().apply {
            string.forEach {
                append(firstOfGroup(it, keepUppercase))
            }
        }.toString()
    }
}
