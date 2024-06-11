package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.filter

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.Category
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.DifficultyRange
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.FailurePercent
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.FailureRange
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.Length
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.SuccessPercent
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.SuccessRange
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter.Type
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterEffect.ACTIVE
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterEffect.BARRIER
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterEffect.BYPASS
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterEffect.SHALLOW_BARRIER
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterEffect.SHALLOW_BYPASS
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R

@JvmName("FilterTypeEffectMessage")
internal fun Type.effectMessage(
    allTypes: Set<Long>,
): Int = when (this.filterEffect(allTypes)) {
    BYPASS -> R.string.filter_type_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_type_effect_shallow_bypass
    ACTIVE -> R.string.filter_type_effect_active
    SHALLOW_BARRIER -> R.string.filter_type_effect_shallow_barrier
    BARRIER -> R.string.filter_type_effect_barrier
}

@JvmName("FilterCategoryEffectMessage")
internal fun Category.effectMessage(
    allCategories: Set<Long>,
): Int = when (this.filterEffect(allCategories)) {
    BYPASS -> R.string.filter_category_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_category_effect_shallow_bypass
    ACTIVE -> R.string.filter_category_effect_active
    SHALLOW_BARRIER -> R.string.filter_category_effect_shallow_barrier
    BARRIER -> R.string.filter_category_effect_barrier
}

@JvmName("FilterLengthEffectMessage")
internal fun Length.effectMessage(): Int = when (this.filterEffect()) {
    BYPASS -> R.string.filter_length_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_length_effect_shallow_bypass
    ACTIVE -> R.string.filter_length_effect_active
    SHALLOW_BARRIER -> R.string.filter_length_effect_shallow_barrier
    BARRIER -> R.string.filter_length_effect_barrier
}

@JvmName("FilterDifficultyEffectMessage")
internal fun DifficultyRange.effectMessage(): Int = when (this.filterEffect()) {
    BYPASS -> R.string.filter_difficulty_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_difficulty_effect_shallow_bypass
    ACTIVE -> R.string.filter_difficulty_effect_active
    SHALLOW_BARRIER -> R.string.filter_difficulty_effect_shallow_barrier
    BARRIER -> R.string.filter_difficulty_effect_barrier
}

@JvmName("FilterFailureEffectMessage")
internal fun FailureRange.effectMessage(): Int = when (this.filterEffect()) {
    BYPASS -> R.string.filter_failure_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_failure_effect_shallow_bypass
    ACTIVE -> R.string.filter_failure_effect_active
    SHALLOW_BARRIER -> R.string.filter_failure_effect_shallow_barrier
    BARRIER -> R.string.filter_failure_effect_barrier
}

@JvmName("FilterSuccessEffectMessage")
internal fun SuccessRange.effectMessage(): Int = when (this.filterEffect()) {
    BYPASS -> R.string.filter_success_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_success_effect_shallow_bypass
    ACTIVE -> R.string.filter_success_effect_active
    SHALLOW_BARRIER -> R.string.filter_success_effect_shallow_barrier
    BARRIER -> R.string.filter_success_effect_barrier
}

@JvmName("FilterFailurePercentEffectMessage")
internal fun FailurePercent.effectMessage(): Int = when (this.filterEffect()) {
    BYPASS -> R.string.filter_failure_percent_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_failure_percent_effect_shallow_bypass
    ACTIVE -> R.string.filter_failure_percent_effect_active
    SHALLOW_BARRIER -> R.string.filter_failure_percent_effect_shallow_barrier
    BARRIER -> R.string.filter_failure_percent_effect_barrier
}

@JvmName("FilterSuccessPercentEffectMessage")
internal fun SuccessPercent.effectMessage(): Int = when (this.filterEffect()) {
    BYPASS -> R.string.filter_success_percent_effect_bypass
    SHALLOW_BYPASS -> R.string.filter_success_percent_effect_shallow_bypass
    ACTIVE -> R.string.filter_success_percent_effect_active
    SHALLOW_BARRIER -> R.string.filter_success_percent_effect_shallow_barrier
    BARRIER -> R.string.filter_success_percent_effect_barrier
}

@JvmName("FilterEffectMessage")
internal fun Filter.effectMessage(
    allTypes: Set<Long>,
    allCategories: Set<Long>,
): Int = when (this) {
    is Type -> effectMessage(allTypes)
    is Category -> effectMessage(allCategories)
    is DifficultyRange -> effectMessage()
    is FailurePercent -> effectMessage()
    is FailureRange -> effectMessage()
    is Length -> effectMessage()
    is SuccessPercent -> effectMessage()
    is SuccessRange -> effectMessage()
}