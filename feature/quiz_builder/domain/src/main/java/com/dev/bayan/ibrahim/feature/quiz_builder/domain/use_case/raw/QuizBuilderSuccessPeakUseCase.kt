package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw

import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class QuizBuilderSuccessPeakUseCase @Inject constructor(
    private val repo: QuizBuilderRepo,
) {
    suspend operator fun invoke(): Int? = repo.setupWordSuccessPeak().first()
}