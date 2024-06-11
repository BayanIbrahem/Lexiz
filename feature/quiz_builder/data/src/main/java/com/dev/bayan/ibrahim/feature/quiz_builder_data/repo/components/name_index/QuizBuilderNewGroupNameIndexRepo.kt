package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index

import kotlinx.coroutines.flow.Flow

interface QuizBuilderNewGroupNameIndexRepo {
    fun setupNewGroupNameIndex(): Flow<Int>
}