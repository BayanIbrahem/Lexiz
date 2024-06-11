package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.upsert

import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.asFilterEntityWithCategoriesTypes
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import javax.inject.Inject

class QuizBuilderUpsertGroupUseCase @Inject constructor(
    private val repo: QuizBuilderRepo,
) {
    suspend operator fun invoke(group: FilterGroup): Long? {
        val groupEntity = FilterGroupEntity(
            group.id,
            group.name.valueOrNull ?: group.id?.toString() ?: "null",
            true
        )
        return if (group.id != null) {
            repo.setupUpdateGroup(
                groupEntity,
                group.filters.map { it.asFilterEntityWithCategoriesTypes(false) })
        } else {
            repo.setupInsertGroup(
                groupEntity,
                group.filters.map { it.asFilterEntityWithCategoriesTypes(false) })
        }
    }
}