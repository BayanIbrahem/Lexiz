package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw

import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizBuilderAllLanguagesUseCase @Inject constructor(
    private val repo: QuizBuilderRepo,
) {
    operator fun invoke(): Flow<List<LanguageItem>> = repo.setupAllLanguages().map {

        it.map { language ->
            LanguageItem(
                languageCode = language.language_code,
                selfDisplayName = repo.getLanguageSelfDisplayName(language.language_code),
                localDisplayName = repo.getLanguageLocalDisplayName(language.language_code),
            )
        }
    }
}