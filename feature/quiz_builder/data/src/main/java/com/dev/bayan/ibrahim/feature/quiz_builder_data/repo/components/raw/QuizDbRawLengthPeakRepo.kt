package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw

import kotlinx.coroutines.flow.Flow

interface QuizDbRawLengthPeakRepo {
    fun setupWordLengthPeak(): Flow<Int?>
}