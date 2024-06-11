package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter

import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.filter.FilterValueEvent

internal sealed interface NewFilterEvent {
    data object InitScreen : NewFilterEvent
    data class OnToggleSaveAsNewCopy(val wasSelected: Boolean) : NewFilterEvent
    data object OnShowAll : NewFilterEvent
    data class OnNameChange(val newName: String) : NewFilterEvent
    data class OnSelectType(val key: Int) : NewFilterEvent
    data class OnFilterValueChange(val filterValueEvent: FilterValueEvent) : NewFilterEvent
    data object OnConfirm : NewFilterEvent
    data object OnCancel : NewFilterEvent
    data object OnApplyWithoutSave : NewFilterEvent

    fun onApplyEvent(
        value: NewFilterUiState,
        getDefaultValue: (Int) -> Filter,
    ): NewFilterUiState {
        return when (this) {
            is OnFilterValueChange -> {
                value.selectedFilter?.run {
                    value.copy(
                        selectedFilter = filterValueEvent.onApplyEvent(this)
                    )
                } ?: value
            }

            is OnNameChange -> {
                value.selectedFilter?.run {
                    value.copy(
                        selectedFilter = this.copyContent(name = newName.asDynamicString())
                    )
                } ?: value
            }

            is OnSelectType -> {
                value.copy(
                    selectedFilter = getDefaultValue(key)
                )
            }

            OnShowAll -> value.copy(showAll = !value.showAll)
            is OnToggleSaveAsNewCopy -> value.copy(saveAsNewCopy = !wasSelected)
            else -> value
        }
    }

}
