package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.Quiz
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.asFilterGroupList
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizBuilderTemplateQuizUseCase @Inject constructor(
    private val repo: QuizBuilderRepo,
) {
    operator fun invoke(): Flow<List<Quiz>> =
        repo.setupTemplateQuizzes().map {
            it.map { (quiz, groups, words) ->
                Quiz(
                    id = quiz.id!!,
                    name = quiz.name,
                    groups = groups.asFilterGroupList(),
                    words = words.mapNotNull { it.name ?: it.description }
                )
            }
        }
}