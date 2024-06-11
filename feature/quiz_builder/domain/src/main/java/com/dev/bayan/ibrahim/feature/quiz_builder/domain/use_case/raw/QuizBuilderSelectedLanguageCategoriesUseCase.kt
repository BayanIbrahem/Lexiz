package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw

import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawSelectedLanguageCategoriesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawSelectedLanguageTypesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizBuilderSelectedLanguageCategoriesUseCase @Inject constructor(
    val repo: QuizDbRawSelectedLanguageCategoriesRepo
) {
    suspend operator fun invoke(languageCode: String): List<Long> {
        return withContext(Dispatchers.IO) {
            repo.setupSelectedLanguageCategories(languageCode).first()
        }
    }
}