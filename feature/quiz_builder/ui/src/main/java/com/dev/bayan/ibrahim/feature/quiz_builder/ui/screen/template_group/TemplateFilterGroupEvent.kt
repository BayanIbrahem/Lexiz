package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group

import com.dev.bayan.ibrahim.core.common.JaArUiEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.QuizSetupEvent

internal sealed interface TemplateFilterGroupEvent : JaArUiEvent<TemplateFilterGroupUiState> {
    data class OnClickGroup(val index: Int) : TemplateFilterGroupEvent
    data class OnEditGroup(val index: Int) : TemplateFilterGroupEvent
    data class OnRemoveGroup(val index: Int) : TemplateFilterGroupEvent
    data object OnCancel : TemplateFilterGroupEvent

    override fun onApplyEvent(uiState: TemplateFilterGroupUiState): TemplateFilterGroupUiState {
        return when (this) {
            is OnRemoveGroup -> uiState.copy(groups = uiState.groups.removeAt(index))
            else -> uiState
        }
    }
}