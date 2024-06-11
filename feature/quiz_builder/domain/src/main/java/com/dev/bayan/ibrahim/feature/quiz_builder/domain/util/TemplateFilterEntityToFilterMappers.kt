package com.dev.bayan.ibrahim.feature.quiz_builder.domain.util

import com.dev.bayan.ibrahim.core.common.tuples.tof
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.FilterCrossCategoryTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.*
import kotlinx.collections.immutable.toPersistentSet
import kotlin.math.roundToInt


internal fun List<FilterCrossCategoryTypeRelation>.asFilterList(): List<Filter> =
    mapNotNull { (filter, categories, types) ->
        when (filter.type) {
            FILTER_TYPE_KEY -> Filter.Type(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                selectedValues = types.map { it.id }.toPersistentSet(),
            )

            FILTER_CATEGORY_KEY -> Filter.Category(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                selectedValues = categories.mapNotNull { it.id }.toPersistentSet(),
            )

            FILTER_LENGTH_KEY -> Filter.Length(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                value = filter.primary_value!!.roundToInt()..filter.secondary_value!!.roundToInt(),
                validRange = 0..10,
            )

            FILTER_DIFFICULTY_RANGE_KEY -> Filter.DifficultyRange(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                value = filter.primary_value!!.roundToInt()..filter.secondary_value!!.roundToInt(),
            )

            FILTER_FAILURE_RANGE_KEY -> Filter.FailureRange(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                value = filter.primary_value!!.roundToInt()..filter.secondary_value!!.roundToInt(),
                validRange = 0..10,
            )

            FILTER_SUCCESS_RANGE_KEY -> Filter.SuccessRange(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                value = filter.primary_value!!.roundToInt()..filter.secondary_value!!.roundToInt(),
                validRange = 0..10,
            )

            FILTER_FAILURE_PERCENT_KEY -> Filter.FailurePercent(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                value = filter.primary_value!!.roundToInt()..filter.secondary_value!!.roundToInt(),
            )

            FILTER_SUCCESS_PERCENT_KEY -> Filter.SuccessPercent(
                id = filter.id,
                name = filter.name.asDynamicString(),
                saveStatus = BuilderItemSaveStatus.SAVED,
                value = filter.primary_value!!.roundToInt()..filter.secondary_value!!.roundToInt(),
            )

            else -> null
        }
    }

internal fun Filter.asFilterEntityWithCategoriesTypes(templateIfNew: Boolean): Triple<FilterEntity, List<Long>, List<Long>> {
    val types = if (this is Filter.Type) {
        (this as FilterType.MultiChoice).selectedValues.toList()
    } else listOf()
    val categories = if (this is Filter.Category) {
        (this as FilterType.MultiChoice).selectedValues.toList()
    } else listOf()
    return FilterEntity(
        id = id,
        name = name.valueOrNull ?: id.toString(),
        type = key,
        primary_value = when (this) {
            is Filter.Category -> null
            is Filter.DifficultyRange -> value.first.toFloat()
            is Filter.FailurePercent -> value.first.toFloat()
            is Filter.FailureRange -> value.first.toFloat()
            is Filter.Length -> value.first.toFloat()
            is Filter.SuccessPercent -> value.first.toFloat()
            is Filter.SuccessRange -> value.first.toFloat()
            is Filter.Type -> null
        },
        secondary_value = when (this) {
            is Filter.Category -> null
            is Filter.DifficultyRange -> value.last.toFloat()
            is Filter.FailurePercent -> value.last.toFloat()
            is Filter.FailureRange -> value.last.toFloat()
            is Filter.Length -> value.last.toFloat()
            is Filter.SuccessPercent -> value.last.toFloat()
            is Filter.SuccessRange -> value.last.toFloat()
            is Filter.Type -> null
        },
        tertiary_value = null,
        template = templateIfNew || id != null,
    ) to types tof categories
}