package com.dev.bayan.ibrahim.feature.quiz_builder.domain.util

sealed class BuilderFilterSavableValueTypeModel {
    data class Range(
        val first: Int,
        val last: Int?,
    ) : BuilderFilterSavableValueTypeModel()

    data class Number(
        val value: Int,
    ) : BuilderFilterSavableValueTypeModel()

    data class MultiChoice(
        val type: BuilderSavableFilterMultiChoiceModel,
        val selectedIds: List<Long>,
    ) : BuilderFilterSavableValueTypeModel()

    data class SingleChoice(
        val type: BuilderSavableFilterSingleChoiceModel,
        val selectedIndex: Long,
    ) : BuilderFilterSavableValueTypeModel()
}

enum class BuilderSavableFilterMultiChoiceModel {
    TYPE,
    CATEGORY,
}

enum class BuilderSavableFilterSingleChoiceModel {
    NONE
}
