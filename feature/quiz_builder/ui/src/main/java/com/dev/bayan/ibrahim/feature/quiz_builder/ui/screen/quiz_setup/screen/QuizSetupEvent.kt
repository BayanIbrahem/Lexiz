package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen

import com.dev.bayan.ibrahim.core.common.JaArUiEvent
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.fab_optoins.QuizFabOptions
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences.QuizTimePerWord
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences.QuizWordsCount
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.top_app_bar.QuizTopAppBarDropDownMenu

internal sealed interface QuizSetupEvent : JaArUiEvent<QuizSetupUiState> {
    data object InitScreen : QuizSetupEvent
    data class OnTimeValueChange(val time: QuizTimePerWord) : QuizSetupEvent
    data class OnCountValueChange(val count: QuizWordsCount) : QuizSetupEvent
    data class OnEditGroup(val index: Int) : QuizSetupEvent
    data class OnRemoveGroup(val index: Int) : QuizSetupEvent

    data object OnAddNewGroup : QuizSetupEvent
    data object OnAddTemplateGroup : QuizSetupEvent
    data class OnNameValueChange(val newName: String) : QuizSetupEvent
    data object OnNavigateUp : QuizSetupEvent
    data class OnClickTopAppBarMenuItem(val item: QuizTopAppBarDropDownMenu) : QuizSetupEvent
    data class OnFirstLanguageSelectItem(val language: LanguageItem?) : QuizSetupEvent
    data class OnSecondLanguageSelectItem(val language: LanguageItem?) : QuizSetupEvent
    data object OnInvertSelectedLanguages : QuizSetupEvent

    data class OnFabOption(val option: QuizFabOptions) : QuizSetupEvent

    override fun onApplyEvent(uiState: QuizSetupUiState): QuizSetupUiState {
        return when (this) {
            is OnClickTopAppBarMenuItem -> {
                if (item == QuizTopAppBarDropDownMenu.CONSUME) {
                    uiState.copy(consumeTemplate = !uiState.consumeTemplate)
                } else uiState
            }

            is OnCountValueChange -> uiState.copy(maxWordsCount = count)
            is OnFirstLanguageSelectItem -> uiState.copy(firstLanguage = language)
            is OnSecondLanguageSelectItem -> uiState.copy(secondLanguage = language)
            OnInvertSelectedLanguages -> uiState.copy(
                firstLanguage = uiState.secondLanguage,
                secondLanguage = uiState.firstLanguage
            )

            is OnNameValueChange -> uiState.copy(name = newName.asDynamicString())
            is OnRemoveGroup -> uiState.copy(groups = uiState.groups.removeAt(index))
            is OnTimeValueChange -> uiState.copy(maxTimePerWord = time)
            else -> uiState
        }
    }
}