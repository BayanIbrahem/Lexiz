package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw

import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import kotlinx.coroutines.flow.Flow

interface QuizDbRawAllTypesRepo {
    fun setupAllTypes(): Flow<List<TypeEntity>>
}