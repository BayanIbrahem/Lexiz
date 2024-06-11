package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw

import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawSelectedLanguageTypesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizBuilderSelectedLanguageTypesUseCase @Inject constructor(
    val repo: QuizDbRawSelectedLanguageTypesRepo
) {
    suspend operator fun invoke(languageCode: String): List<Long> {
        return withContext(Dispatchers.IO) {
            repo.setupSelectedLanguageTypes(languageCode).first()
        }
    }
}