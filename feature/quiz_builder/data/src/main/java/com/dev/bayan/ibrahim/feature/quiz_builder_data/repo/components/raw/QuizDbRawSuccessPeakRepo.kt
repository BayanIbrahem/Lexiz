package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw

import kotlinx.coroutines.flow.Flow

interface QuizDbRawSuccessPeakRepo {
    fun setupWordSuccessPeak(): Flow<Int?>
}