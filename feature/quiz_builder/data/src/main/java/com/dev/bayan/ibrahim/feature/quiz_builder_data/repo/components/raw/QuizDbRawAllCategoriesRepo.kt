package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw

import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface QuizDbRawAllCategoriesRepo {
    fun setupAllCategories(): Flow<List<CategoryEntity>>
}