package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.asFilterGroupList
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizBuilderTemplateGroupUseCase @Inject constructor(
    private val repo: QuizBuilderRepo,
) {
    operator fun invoke(): Flow<List<FilterGroup>> = repo.setupTemplateGroups().map {
        it.asFilterGroupList()
    }
}