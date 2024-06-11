package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.upsesrt

import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity

interface QuizDbSetupUpsertGroupRepo {
    suspend fun setupInsertGroup(
        entity: FilterGroupEntity,
        data: List<Triple<FilterEntity, List<Long>, List<Long>>>
    ): Long?

    suspend fun setupUpdateGroup(
        entity: FilterGroupEntity,
        data: List<Triple<FilterEntity, List<Long>, List<Long>>>
    ): Long?
}