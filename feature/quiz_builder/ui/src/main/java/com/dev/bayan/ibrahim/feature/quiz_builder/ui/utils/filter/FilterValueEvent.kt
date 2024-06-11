package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.filter

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter

sealed interface FilterValueEvent {
    data class OnRangeChange(val newRange: IntRange) : FilterValueEvent
    data class OnValueChange(val newValue: Int) : FilterValueEvent
    data class OnSelectItem(val newId: Long) : FilterValueEvent

    fun onApplyEvent(value: Filter): Filter {
        return when (this) {
            is OnRangeChange -> {
                when (value) {
                    is Filter.DifficultyRange -> value.copy(value = newRange)
                    is Filter.FailurePercent -> value.copy(value = newRange)
                    is Filter.FailureRange -> value.copy(value = newRange)
                    is Filter.Length -> value.copy(value = newRange)
                    is Filter.SuccessPercent -> value.copy(value = newRange)
                    is Filter.SuccessRange -> value.copy(value = newRange)
                    else -> value
                }
            }

            is OnSelectItem -> {
                when (value) {
                    is Filter.Category -> value.copy(
                        selectedValues = value.selectedValues.run {
                            if (newId in this) remove(newId)
                            else add(newId)
                        }
                    )

                    is Filter.Type -> value.copy(
                        selectedValues = value.selectedValues.run {
                            if (newId in this) remove(newId)
                            else add(newId)
                        }
                    )

                    else -> value
                }
            }

            is OnValueChange -> {
                when (value) {
                    // currently there is no filters of percent
                    else -> value
                }
            }
        }
    }
}

