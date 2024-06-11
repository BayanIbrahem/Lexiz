package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw

import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawAllCategoriesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizBuilderAllCategoriesUseCase(
    private val repo: QuizDbRawAllCategoriesRepo
) {
    operator fun invoke(): Flow<List<CategoryItem>> = repo.setupAllCategories().map {
        it.map { category ->
            CategoryItem(
                id = category.id!!,
                name = category.name,
                description = category.description
            )
        }
    }
}