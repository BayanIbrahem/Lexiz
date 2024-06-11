package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw

import kotlinx.coroutines.flow.Flow

interface QuizDbRawFailurePeakRepo {
    fun setupWordFailurePeak(): Flow<Int?>
}