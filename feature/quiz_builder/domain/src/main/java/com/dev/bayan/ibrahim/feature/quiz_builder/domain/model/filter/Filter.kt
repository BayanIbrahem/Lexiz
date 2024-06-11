package com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter

import com.dev.bayan.ibrahim.core.common.range.contains
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.Category
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.DifficultyRange
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.FailurePercent
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.FailureRange
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.Length
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.SuccessPercent
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.SuccessRange
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.Type
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf


const val FILTER_TYPE_KEY = 0
const val FILTER_CATEGORY_KEY = 1
const val FILTER_LENGTH_KEY = 2
const val FILTER_DIFFICULTY_RANGE_KEY = 3
const val FILTER_FAILURE_RANGE_KEY = 4
const val FILTER_SUCCESS_RANGE_KEY = 5
const val FILTER_FAILURE_PERCENT_KEY = 6
const val FILTER_SUCCESS_PERCENT_KEY = 7

val FILTER_TYPE by lazy { Type() }
val FILTER_CATEGORY by lazy { Category() }
val FILTER_LENGTH by lazy { Length() }
val FILTER_DIFFICULTY_RANGE by lazy { DifficultyRange() }
val FILTER_FAILURE_RANGE by lazy { FailureRange() }
val FILTER_SUCCESS_RANGE by lazy { SuccessRange() }
val FILTER_FAILURE_PERCENT by lazy { FailurePercent() }
val FILTER_SUCCESS_PERCENT by lazy { SuccessPercent() }

val FILTER_KEYS = persistentSetOf(
    FILTER_TYPE_KEY,
    FILTER_CATEGORY_KEY,
    FILTER_LENGTH_KEY,
    FILTER_DIFFICULTY_RANGE_KEY,
    FILTER_FAILURE_RANGE_KEY,
    FILTER_SUCCESS_RANGE_KEY,
    FILTER_FAILURE_PERCENT_KEY,
    FILTER_SUCCESS_PERCENT_KEY,
)

/**
 * @property defaultInstanceName default name res (which provides a %s for a value) used to get default name for unnamed filterTypes
 * @property resName name of this class of filterTypes
 * @property resHint hint of this class of filterTypes
 * @property Type word type filter (word class)
 * @property Category word category filter
 * @property DifficultyRange filter of word by difficulty according to other words like (difficulty in 0-20% or 40-60%)
 * @property Length filter by the length of the word (in chars)
 * @property FailureRange filter by the failure count of the word
 * @property SuccessRange filter by the success count of the word
 * @property FailurePercent filter by the failure percent of the word
 * @property SuccessPercent filter by the success percent of the word
 * @property effect true if the filter will do nothing (not now not later)
 * @suppress **FOR DEVELOPER, FOR ADDING ANY NEW FILTER**
 * - ADD KEY WITH UNIQUE VALUE LIKE [FILTER_TYPE_KEY]
 * - ADD THE KEY YOU ADDED TO THE KEYS LIST
 * - ADD THE FILTER YOU ADDED TO THE [Filter.getDefaultFilters] LIST
 */
sealed interface Filter : FilterType {
    val defaultInstanceName: Int
    val resName: Int
    val resHint: Int
    val key: Int
    fun getDefaultName(index: Int) = JaArDynamicString.StrRes(
        id = defaultInstanceName,
        arg = index.toString()
    )

    fun filterEffect(allTypes: Set<Long>, allCategories: Set<Long>): FilterEffect = when (this) {
        is Category -> filterEffect(allCategories)
        is DifficultyRange -> filterEffect()
        is FailurePercent -> filterEffect()
        is FailureRange -> filterEffect()
        is Length -> filterEffect()
        is SuccessPercent -> filterEffect()
        is SuccessRange -> filterEffect()
        is Type -> filterEffect(allTypes)
    }


    companion object Companion {
        fun getDefaultFilters() = persistentListOf(
            FILTER_TYPE,
            FILTER_CATEGORY,
            FILTER_LENGTH,
            FILTER_DIFFICULTY_RANGE,
            FILTER_FAILURE_RANGE,
            FILTER_SUCCESS_RANGE,
            FILTER_FAILURE_PERCENT,
            FILTER_SUCCESS_PERCENT,
        )
    }

    fun copyContent(
        id: Long? = this.id,
        name: JaArDynamicString = this.name,
        saveStatus: BuilderItemSaveStatus = this.saveStatus,
    ): Filter = when (this) {
        is Category -> copy(id, name, saveStatus)
        is DifficultyRange -> copy(id, name, saveStatus)
        is FailurePercent -> copy(id, name, saveStatus)
        is FailureRange -> copy(id, name, saveStatus)
        is Length -> copy(id, name, saveStatus)
        is SuccessPercent -> copy(id, name, saveStatus)
        is SuccessRange -> copy(id, name, saveStatus)
        is Type -> copy(id, name, saveStatus)
    }

    data class Type(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val selectedValues: PersistentSet<Long> = persistentSetOf(),
    ) : Filter, FilterType.MultiChoice {
        override val defaultInstanceName = R.string.filterType_type_defaultName
        override val resName = R.string.filterType_type_resName
        override val resHint = R.string.filterType_type_resHint
        override val key = FILTER_TYPE_KEY
        override val validateEmptySelection = false
        fun filterEffect(
            allTypes: Set<Long>,
        ): FilterEffect {
            return if (selectedValues.isEmpty()) {
                FilterEffect.BARRIER
            } else if (selectedValues == allTypes) {
                FilterEffect.SHALLOW_BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }


    data class Category(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val selectedValues: PersistentSet<Long> = persistentSetOf(),
    ) : Filter, FilterType.MultiChoice {
        override val defaultInstanceName = R.string.filterType_category_defaultName
        override val resName = R.string.filterType_category_resName
        override val resHint = R.string.filterType_category_resHint
        override val key = FILTER_CATEGORY_KEY
        override val validateEmptySelection = false
        fun filterEffect(
            allCategories: Set<Long>,
        ): FilterEffect {
            return if (selectedValues.isEmpty()) {
                FilterEffect.BARRIER
            } else if (selectedValues == allCategories) {
                FilterEffect.SHALLOW_BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }

    data class Length(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val value: IntRange = 0..15,
        override val validRange: IntRange = 0..15,
    ) : Filter, FilterType.Range {
        override val defaultInstanceName = R.string.filterType_length_defaultName
        override val resName = R.string.filterType_length_resName
        override val resHint = R.string.filterType_length_resHint
        override val infinityEnd = true
        override val key = FILTER_LENGTH_KEY
        fun filterEffect(): FilterEffect {
            return if (value.last < 1) {
                // impossible to accor but maybe for any glitch (allow only empty words)
                FilterEffect.BARRIER
            } else if (value.first > validRange.last) {
                // impossible to accor but maybe for any glitch (allow only words that are longer than current longest word)
                FilterEffect.SHALLOW_BARRIER
            } else if (value == validRange) {
                // valid range is from 1 to the current longest word
                FilterEffect.SHALLOW_BYPASS
            } else if (value.first <= 1 && value.last > validRange.last) {
                // valid length is between 1 and infinity
                FilterEffect.BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }

    data class DifficultyRange(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val value: IntRange = 50..100,
    ) : Filter, FilterType.Range {
        override val defaultInstanceName = R.string.filterType_difficulty_defaultName
        override val resName = R.string.filterType_difficulty_resName
        override val resHint = R.string.filterType_difficulty_resHint
        override val validRange = 0..100
        override val infinityEnd = false
        override val key = FILTER_DIFFICULTY_RANGE_KEY
        fun filterEffect(): FilterEffect {
            return if (value.last < 0 || value.first > 100) {
                // impossible to accor but maybe for any glitch (allow nothing invalid percentage range)
                FilterEffect.BARRIER
            }
            // impossible to be a shallow barrier / shallow bypass
            else if (value == 0..100) {
                // any valid percent is between 0 and infinity
                FilterEffect.BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }

    data class FailureRange(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val value: IntRange = 0..15,
        override val validRange: IntRange = 0..15,
    ) : Filter, FilterType.Range {
        override val defaultInstanceName = R.string.filterType_failure_defaultName
        override val resName = R.string.filterType_failure_resName
        override val resHint = R.string.filterType_failure_resHint
        override val infinityEnd = true
        override val key = FILTER_FAILURE_RANGE_KEY
        fun filterEffect(): FilterEffect {
            return if (value.last < 0) {
                // impossible to accor but maybe for any glitch (allow only words that i failed less that zero times 😓)
                FilterEffect.BARRIER
            } else if (value.first > validRange.last) {
                // impossible to accor but maybe for any glitch (allow only words that i failed in more that the current most failed word)
                FilterEffect.SHALLOW_BARRIER
            } else if (value == validRange) {
                // valid range is from 0 to the current most failed word
                FilterEffect.SHALLOW_BYPASS
            } else if (value.first <= 0 && value.last > validRange.last) {
                // any valid range is between 0 and infinity
                FilterEffect.BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }

    data class SuccessRange(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val value: IntRange = 0..15,
        override val validRange: IntRange = 0..15,
    ) : Filter, FilterType.Range {
        override val defaultInstanceName = R.string.filterType_success_defaultName
        override val resName = R.string.filterType_success_resName
        override val resHint = R.string.filterType_success_resHint
        override val infinityEnd = true
        override val key = FILTER_SUCCESS_RANGE_KEY
        fun filterEffect(): FilterEffect {
            return if (value.last < 0) {
                // impossible to accor but maybe for any glitch (allow only words that i succeeded less that zero times 😓)
                FilterEffect.BARRIER
            } else if (value.first > validRange.last) {
                // impossible to accor but maybe for any glitch (allow only words that i succeeded in more that the current most succeeded word)
                FilterEffect.SHALLOW_BARRIER
            } else if (value == validRange) {
                // valid range is from 0 to the current most succeeded word
                FilterEffect.SHALLOW_BYPASS
            } else if (value.first <= 0 && value.last > validRange.last) {
                // any valid range is between 0 and infinity
                FilterEffect.BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }

    data class FailurePercent(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val value: IntRange = 75..100,
    ) : Filter, FilterType.Range {
        override val defaultInstanceName = R.string.filterType_failurePercent_defaultName
        override val resName = R.string.filterType_failurePercent_resName
        override val resHint = R.string.filterType_failurePercent_resHint
        override val validRange = 0..100
        override val infinityEnd = false
        override val key = FILTER_FAILURE_PERCENT_KEY
        fun filterEffect(): FilterEffect {
            return if (value.last < 0 || value.first > 100) {
                // impossible to accor but maybe for any glitch (allow nothing invalid percentage range)
                FilterEffect.BARRIER
            }
            // impossible to be a shallow barrier / shallow bypass
            else if (value == 0..100) {
                // any valid percent is between 0 and infinity
                FilterEffect.BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }

    data class SuccessPercent(
        override val id: Long? = null,
        override val name: JaArDynamicString = JaArDynamicString.Blank,
        override val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
        override val value: IntRange = 75..100,
    ) : Filter, FilterType.Range {
        override val defaultInstanceName = R.string.filterType_successPercent_defaultName
        override val resName = R.string.filterType_successPercent_resName
        override val resHint = R.string.filterType_successPercent_resHint
        override val validRange = 0..100
        override val infinityEnd = false
        override val key = FILTER_SUCCESS_PERCENT_KEY
        fun filterEffect(): FilterEffect {
            return if (value.last < 0 || value.first > 100) {
                // impossible to accor but maybe for any glitch (allow nothing invalid percentage range)
                FilterEffect.BARRIER
            }
            // impossible to be a shallow barrier / shallow bypass
            else if (value == 0..100) {
                // any valid percent is between 0 and infinity
                FilterEffect.BYPASS
            } else {
                FilterEffect.ACTIVE
            }
        }
    }
}

