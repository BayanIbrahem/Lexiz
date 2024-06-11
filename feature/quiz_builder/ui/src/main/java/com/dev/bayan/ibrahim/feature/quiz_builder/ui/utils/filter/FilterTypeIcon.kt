package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.filter

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.core.ui.R as UiRes

internal val Filter.icon: Int
    get() = when (this) {
        is Filter.Category -> UiRes.drawable.ic_category
        is Filter.DifficultyRange -> R.drawable.word_difficulty
        is Filter.FailurePercent -> R.drawable.word_failure_percent
        is Filter.FailureRange -> R.drawable.word_failure
        is Filter.Length -> R.drawable.word_length
        is Filter.SuccessPercent -> R.drawable.word_success_percent
        is Filter.SuccessRange -> R.drawable.word_success
        is Filter.Type -> UiRes.drawable.ic_type
    }