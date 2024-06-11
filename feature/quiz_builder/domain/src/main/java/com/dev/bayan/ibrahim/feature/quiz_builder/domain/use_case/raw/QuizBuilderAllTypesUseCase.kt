package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw

import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawAllTypesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizBuilderAllTypesUseCase(
    private val repo: QuizDbRawAllTypesRepo,
) {
    operator fun invoke(): Flow<List<TypeItem>> = repo.setupAllTypes().map {
        it.map { entity ->
            TypeItem(
                id = entity.id,
                languageCode = entity.language_code,
                name = entity.name,
                description = entity.description
            )
        }
    }
}