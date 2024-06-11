package com.dev.bayan.ibrahim.core.ui.components.dynamic

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource

/**
 * a label either may be a resources int or a string
 * possible values:
 * - [Str]: for string
 * - [StrRes]: for resources
 * @property value return the string value of this object
 * @property key key of the item if needed
 * @see asDynamicString
 * @see Str
 * @see StrRes
 */

sealed interface JaArDynamicString {
    val key: Long?

    //   fun getValue(context: Context): String? = when (this) {
//      is Builder -> null
//      is PluralRes -> context.resources.getQuantityString(id, count, *(args.toTypedArray()))
//      is Str -> str
//      is StrRes -> context.getString(id, *(args.toTypedArray()))
//   }
    val value: String
        @Composable
        get() = when (this) {
            is Str -> str
            is StrRes -> stringResource(id = id, *(args.toTypedArray()))
            is PluralRes -> pluralStringResource(id = id, count = count, *(args.toTypedArray()))
            is Builder -> builder()
        }

    /**
     * string value type of Dynamic string
     * @param str the string value
     * @param key key of the item if needed
     */
    data class Str(
        val str: String,
        override val key: Long? = null,
    ) : JaArDynamicString

    /**
     * string resource value type of Dynamic string
     * @param id the string resource id
     * @param args arguments of this string resource
     * @param key key of the item if needed
     */
    data class StrRes(
        @StringRes val id: Int,
        val args: List<String> = listOf(),
        override val key: Long? = null,
    ) : JaArDynamicString {
        constructor(
            @StringRes id: Int,
            arg: String,
            key: Long? = null,
        ) : this(id = id, args = listOf(arg), key = key)
    }

    /**
     * plural resource value type of Dynamic string
     * @param id the plural resource id
     * @param count count of the item
     * @param args arguments of this string resource
     * @param key key of the item if needed
     */
    data class PluralRes(
        @PluralsRes val id: Int,
        val count: Int,
        val args: List<Any> = listOf(),
        override val key: Long? = null,
    ) : JaArDynamicString {
        constructor(
            @PluralsRes id: Int,
            count: Int,
            arg: Any,
            key: Long? = null,
        ) : this(id = id, count = count, args = listOf(arg), key = key)
    }

    data class Builder(
        override val key: Long? = null,
        val builder: @Composable () -> String,
    ) : JaArDynamicString

    companion object Companion {
        val Blank: JaArDynamicString = Str("")
    }

    val valueOrNull: String?
        get() = when (this) {
            is Str -> str
            is StrRes -> null
            is PluralRes -> null
            is Builder -> null
        }
}

fun String.asDynamicString(): JaArDynamicString = JaArDynamicString.Str(this)