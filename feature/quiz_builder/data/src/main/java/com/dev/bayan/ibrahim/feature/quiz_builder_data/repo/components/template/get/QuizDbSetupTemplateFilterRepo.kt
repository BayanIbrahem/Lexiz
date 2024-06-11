package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.get

import com.dev.bayan.ibrahim.core.database.entities.relation.filter.FilterCrossCategoryTypeRelation
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawAllCategoriesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawAllTypesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawFailurePeakRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawLengthPeakRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawSuccessPeakRepo
import kotlinx.coroutines.flow.Flow

interface QuizDbSetupTemplateFilterRepo :
    QuizDbRawAllTypesRepo,
    QuizDbRawAllCategoriesRepo,
    QuizDbRawLengthPeakRepo,
    QuizDbRawFailurePeakRepo,
    QuizDbRawSuccessPeakRepo {
    fun setupTemplateFilters(): Flow<List<FilterCrossCategoryTypeRelation>>
}