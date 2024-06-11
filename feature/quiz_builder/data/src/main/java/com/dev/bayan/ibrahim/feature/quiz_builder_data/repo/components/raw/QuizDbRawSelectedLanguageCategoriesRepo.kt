package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw

import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import kotlinx.coroutines.flow.Flow

interface QuizDbRawSelectedLanguageCategoriesRepo {
    fun setupSelectedLanguageCategories(code: String): Flow<List<Long>>
}