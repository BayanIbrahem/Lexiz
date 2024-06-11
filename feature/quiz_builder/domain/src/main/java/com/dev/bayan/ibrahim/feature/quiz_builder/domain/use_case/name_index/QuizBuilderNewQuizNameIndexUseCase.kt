package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index

import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewQuizNameIndexRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizBuilderNewQuizNameIndexUseCase @Inject constructor(
    private val repo: QuizBuilderRepo
) {
    operator fun invoke(): Flow<Int> = repo.setupNewQuizNameIndex()
}