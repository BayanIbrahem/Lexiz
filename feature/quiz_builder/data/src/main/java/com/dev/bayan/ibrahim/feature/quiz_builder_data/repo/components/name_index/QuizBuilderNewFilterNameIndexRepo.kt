package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index

import kotlinx.coroutines.flow.Flow

interface QuizBuilderNewFilterNameIndexRepo {
    fun setupNewFilterNameIndex(): Flow<Map<Int, Int>>
}