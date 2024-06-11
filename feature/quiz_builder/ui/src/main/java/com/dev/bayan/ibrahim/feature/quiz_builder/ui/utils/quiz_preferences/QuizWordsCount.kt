package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences

import androidx.annotation.StringRes
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import com.dev.bayan.ibrahim.core.ui.R as UiRes

enum class QuizWordsCount(
    val distinctCount: Int,
    @StringRes val resLabel: Int
) {
    SHORTEST(distinctCount = 10, UiRes.string.the_shortest),
    SHORT(distinctCount = 20, UiRes.string.short_),
    MODERATE(distinctCount = 30, UiRes.string.normal),
    LONG(distinctCount = 40, UiRes.string.long_),
    LONGEST(distinctCount = 50, UiRes.string.the_longest);

    companion object {
        fun asLabelsPersistentList(): PersistentList<JaArDynamicString> {
            return entries.map {
                JaArDynamicString.StrRes(it.resLabel)
            }.toPersistentList()
        }
    }
}