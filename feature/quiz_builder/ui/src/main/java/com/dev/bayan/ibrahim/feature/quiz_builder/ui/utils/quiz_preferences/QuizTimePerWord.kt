package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences

import androidx.annotation.StringRes
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import com.dev.bayan.ibrahim.core.ui.R as UiRes

internal enum class QuizTimePerWord(
    val sec: Int,
    @StringRes val resLabel: Int
) {
    FASTEST(sec = 5, resLabel = UiRes.string.the_fastest),
    FAST(sec = 10, resLabel = UiRes.string.fast),
    MODERATE(sec = 15, resLabel = UiRes.string.normal),
    SLOW(sec = 20, resLabel = UiRes.string.slow),
    SLOWEST(sec = 25, resLabel = UiRes.string.the_slowest);

    companion object {
        fun asLabelsPersistentList(): PersistentList<JaArDynamicString> {
            return QuizTimePerWord.entries.map {
                JaArDynamicString.StrRes(it.resLabel)
            }.toPersistentList()
        }
    }
}
