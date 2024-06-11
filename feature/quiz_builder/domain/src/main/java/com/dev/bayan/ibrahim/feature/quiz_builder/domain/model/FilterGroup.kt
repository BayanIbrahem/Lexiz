package com.dev.bayan.ibrahim.feature.quiz_builder.domain.model

import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class FilterGroup(
    val id: Long? = null,
    val name: JaArDynamicString = JaArDynamicString.Blank,
    val filters: PersistentList<Filter> = persistentListOf(),
    val saveAsNewCopy: Boolean = false,
    val saveStatus: BuilderItemSaveStatus = BuilderItemSaveStatus.NEW,
) {
    val validData = filters.isNotEmpty()
}
