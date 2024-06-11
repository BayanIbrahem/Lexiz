package com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter

import com.dev.bayan.ibrahim.core.common.range.contains
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import kotlinx.collections.immutable.PersistentSet

sealed interface FilterType {
    val id: Long?
    val name: JaArDynamicString
    val saveStatus: BuilderItemSaveStatus
    val validData: Boolean

    val valueAsString: String

    sealed interface Range : FilterType {
        val value: IntRange
        val validRange: IntRange
        val infinityEnd: Boolean
        override val validData: Boolean
            get() = value.first >= validRange.first
                    && (value.last <= validRange.last || infinityEnd)
        override val valueAsString
            get() = value.toString()
    }

    sealed interface Percent : FilterType {
        val value: Int
        val validRange: IntRange
        override val validData: Boolean
            get() = value in validRange && (0..100).contains(validRange)
        override val valueAsString get() = value.toString()
    }

    sealed interface Number : FilterType {
        val value: Int
        val validRange: IntRange
        override val validData: Boolean
            get() = value in validRange
        override val valueAsString get() = value.toString()
    }

    sealed interface MultiChoice : FilterType {
        val selectedValues: PersistentSet<Long>
        val validateEmptySelection: Boolean
        override val validData: Boolean
            get() = selectedValues.isNotEmpty() || validateEmptySelection
        override val valueAsString get() = "#${selectedValues.count()}"
    }

    sealed interface SingleChoice : FilterType {
        val selectedValue: Long?
        val validateEmptySelection: Boolean
        override val validData: Boolean get() = selectedValue != null || validateEmptySelection
        override val valueAsString get() = selectedValue?.run { "1" } ?: "0"
    }
}

