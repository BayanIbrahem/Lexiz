package com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index

import android.util.Log
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewFilterNameIndexRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizBuilderNewFilterNameIndexUseCase @Inject constructor(
    private val repo: QuizBuilderRepo
) {
    operator fun invoke(): Flow<Map<Int, Int>> = repo.setupNewFilterNameIndex().also {
        it.map {
            Log.d("filter_name", "use case map: $it")
            it
        }
    }
}