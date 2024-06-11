package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group

import com.dev.bayan.ibrahim.core.common.JaArUiEvent
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup

internal sealed interface NewFilterGroupEvent : JaArUiEvent<FilterGroup> {
    data object InitScreen : NewFilterGroupEvent
    data class OnTitleChange(val newTitle: String) : NewFilterGroupEvent

    data class OnToggleSaveAsCopy(val oldValue: Boolean) : NewFilterGroupEvent

    data object OnAddNewFilter : NewFilterGroupEvent

    data object OnAddTemplateFilter : NewFilterGroupEvent

    data class OnRemoveFilter(val index: Int) : NewFilterGroupEvent

    data class OnEditFilter(val index: Int) : NewFilterGroupEvent

    data object OnConfirm : NewFilterGroupEvent

    data object OnCancel : NewFilterGroupEvent

    data object OnApplyWithoutSave : NewFilterGroupEvent

    override fun onApplyEvent(uiState: FilterGroup): FilterGroup {
        return when (this) {
            is OnRemoveFilter -> uiState.copy(filters = uiState.filters.removeAt(index))
            is OnTitleChange -> uiState.copy(name = newTitle.asDynamicString())
            is OnToggleSaveAsCopy -> uiState.copy(saveAsNewCopy = !oldValue)
            else -> uiState
        }
    }
}
