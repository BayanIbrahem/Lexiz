package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.upsesrt

import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity

interface QuizDbSetupUpsertFilterRepo {
    suspend fun setupInsertFilter(
        entity: FilterEntity,
        types: List<Long>,
        categories: List<Long>
    ): Long?

    suspend fun setupUpdateFilter(
        entity: FilterEntity,
        newTypes: List<Long>,
        newCategories: List<Long>
    ): Long?
}