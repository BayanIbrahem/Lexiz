package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class TemplateFilterGroupUiState(
    val groups: PersistentList<FilterGroup> = persistentListOf(),
)
