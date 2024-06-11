package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.manager.primitive_condition

import com.dev.bayan.ibrahim.core.common.range.mapFromPercentageRange
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterEffect
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterType

/**
 * @property types word type must be in this list
 * @property categories word categories contains at least one of the those categories
 * @property lengths word length is between those lengths
 * @property difficulties word difficulty must be between those difficulties
 * @property failures word failures must be between those failures
 * @property successes word failures must be between those successes
 */
interface SimplifiedWordFilterConditions {
    val types: Set<Long>?
    val categories: Set<Long>?
    val lengths: Set<Int>?
    val difficulties: Set<Int>?
    val failures: Set<Int>?
    val successes: Set<Int>?

    fun validateEffect(hasNoCategoriesWords: Boolean): FilterEffect {
        val effects = listOf(
            types.parameterEffect(),
            categories.parameterEffect(!hasNoCategoriesWords),
            lengths.parameterEffect(),
            difficulties.parameterEffect(),
            failures.parameterEffect(),
            successes.parameterEffect(),
        )
        return if (effects.any { it.isBarrier }) {
            FilterEffect.SHALLOW_BARRIER
        } else if (effects.all { it.isBypass }) {
            FilterEffect.BYPASS
        } else {
            FilterEffect.ACTIVE
        }
    }

    private fun Set<*>?.parameterEffect(extraBarrierCondition: Boolean = true) = if (this == null) {
        FilterEffect.SHALLOW_BYPASS
    } else if (this.isEmpty() && extraBarrierCondition) {
        FilterEffect.SHALLOW_BARRIER
    } else {
        FilterEffect.ACTIVE
    }
}

class MutableSimplifiedWordFilterConditions : SimplifiedWordFilterConditions {
    override var types: MutableSet<Long>? = null
    override var categories: MutableSet<Long>? = null
    override var lengths: MutableSet<Int>? = null
    override var difficulties: MutableSet<Int>? = null
    override var failures: MutableSet<Int>? = null
    override var successes: MutableSet<Int>? = null
}

suspend fun simplifyConditions(
    groups: Collection<FilterGroup>,
    languageTypes: List<Long>,
    languageCategories: List<Long>,
    failures: Set<Int>,
    succeeds: Set<Int>,
    lengths: Set<Int>,
): SimplifiedWordFilterConditions {
    val conditions = MutableSimplifiedWordFilterConditions()
    groups.forEach { group ->
        val failed = failures.min()..failures.max()
        val succeeded = succeeds.min()..succeeds.max()

        val failurePercentResult: MutableSet<Int> = mutableSetOf()
        val failureRangeResult: MutableSet<Int> = mutableSetOf()

        val successPercentResult: MutableSet<Int> = mutableSetOf()
        val successRangeResult: MutableSet<Int> = mutableSetOf()
        group.filters.forEach { filter ->

            when (filter) {
                is Filter.Type -> {
                    conditions.types?.addAll(filter.selectedValues) ?: run {
                        conditions.types = filter.selectedValues.toMutableSet()
                    }
                }

                is Filter.Category -> {
                    conditions.categories?.addAll(filter.selectedValues) ?: run {
                        conditions.categories = filter.selectedValues.toMutableSet()
                    }
                }

                is Filter.Length -> {
                    val values = lengths.filter(filter::validate)
                    conditions.lengths?.addAll(values) ?: run {
                        conditions.lengths = mutableSetOf(*(values.toList().toTypedArray()))
                    }
                }

                is Filter.DifficultyRange -> {
                    conditions.difficulties?.addAll(filter.value) ?: run {
                        conditions.difficulties = filter.value.toMutableSet()
                    }
                }

                is Filter.FailurePercent -> {
                    val minAccepted = filter.value.first.mapFromPercentageRange(failed)
                    val maxAccepted = filter.value.first.mapFromPercentageRange(failed)
                    val acceptanceRange = minAccepted..maxAccepted
                    failurePercentResult.addAll(failures.filter { it in acceptanceRange })
                }

                is Filter.FailureRange -> {
                    failureRangeResult.addAll(failures.filter(filter::validate))
                }

                is Filter.SuccessPercent -> {
                    val minAccepted = filter.value.first.mapFromPercentageRange(succeeded)
                    val maxAccepted = filter.value.first.mapFromPercentageRange(succeeded)
                    val acceptanceRange = minAccepted..maxAccepted
                    successPercentResult.addAll(succeeds.filter { it in acceptanceRange })
                }

                is Filter.SuccessRange -> {
                    successRangeResult.addAll(failures.filter(filter::validate))
                }
            }
        }

        val failureRange = failureRangeResult intersect failurePercentResult
        conditions.failures?.addAll(failureRange) ?: run {
            conditions.failures = failureRange.toMutableSet()
        }

        val successRange = successRangeResult intersect successPercentResult
        conditions.successes?.addAll(successRange) ?: run {
            conditions.successes = successRange.toMutableSet()
        }
    }

    conditions.types?.removeAll { it !in languageTypes }
    conditions.categories?.removeAll { it !in languageCategories }
    return conditions
}

private fun FilterType.Range.validate(num: Int): Boolean =
    num in value || (num > value.last && infinityEnd)
