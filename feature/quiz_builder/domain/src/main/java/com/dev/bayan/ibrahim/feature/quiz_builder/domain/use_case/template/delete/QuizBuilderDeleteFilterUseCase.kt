package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.delete

import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import javax.inject.Inject

class QuizBuilderDeleteFilterUseCase @Inject constructor(
    private val repo: QuizBuilderRepo,
) {
    suspend operator fun invoke(id: Long): Boolean {
        return repo.setupDeleteFilters(id) > 0
    }
}