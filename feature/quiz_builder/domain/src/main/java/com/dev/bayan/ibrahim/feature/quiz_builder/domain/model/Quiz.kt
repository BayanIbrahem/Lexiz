package com.dev.bayan.ibrahim.feature.quiz_builder.domain.model

data class Quiz(
    val id: Long?,
    val name: String,
    val groups: List<FilterGroup>,
    val words: List<String>,
)
