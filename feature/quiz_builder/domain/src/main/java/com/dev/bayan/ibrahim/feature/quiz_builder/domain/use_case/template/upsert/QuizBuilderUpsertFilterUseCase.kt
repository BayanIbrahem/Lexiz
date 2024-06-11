package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.upsert

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.asFilterEntityWithCategoriesTypes
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import javax.inject.Inject

class QuizBuilderUpsertFilterUseCase @Inject constructor(
    private val repo: QuizBuilderRepo,
) {
    suspend operator fun invoke(filter: Filter): Long? {
        return filter.asFilterEntityWithCategoriesTypes(
            templateIfNew = true,
        ).let { (filter, types, categories) ->
            if (filter.id != null) {
                repo.setupUpdateFilter(
                    filter,
                    newTypes = types,
                    newCategories = categories,
                )
            } else {
                repo.setupInsertFilter(
                    filter,
                    types = types,
                    categories = categories,
                )
            }
        }
    }
}