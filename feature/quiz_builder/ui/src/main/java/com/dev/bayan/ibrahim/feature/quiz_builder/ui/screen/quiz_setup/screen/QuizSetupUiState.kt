package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen

import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.Quiz
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences.QuizTimePerWord
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences.QuizWordsCount
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class QuizSetupUiState(
    val id: Long? = null,
    val name: JaArDynamicString = JaArDynamicString.Blank,

    val firstLanguage: LanguageItem? = null,
    val secondLanguage: LanguageItem? = null,

    val maxTimePerWord: QuizTimePerWord = QuizTimePerWord.MODERATE,
    val maxWordsCount: QuizWordsCount = QuizWordsCount.MODERATE,

    val savingState: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,

    val groups: PersistentList<FilterGroup> = persistentListOf(),

    val consumeTemplate: Boolean = false,
)

internal fun QuizSetupUiState.asRuleBasedQuiz(copy: Boolean): Quiz {
    return Quiz(
        id = if (copy) null else id,
        name = name.valueOrNull ?: "",
        groups = this.groups,
        words = listOf(),
    )
}