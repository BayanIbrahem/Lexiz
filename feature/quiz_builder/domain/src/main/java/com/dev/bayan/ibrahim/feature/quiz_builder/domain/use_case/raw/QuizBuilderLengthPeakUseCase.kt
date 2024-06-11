package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw

import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawLengthPeakRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class QuizBuilderLengthPeakUseCase(
    private val repo: QuizDbRawLengthPeakRepo,
) {
    suspend operator fun invoke(): Int? = repo.setupWordLengthPeak().first()
}