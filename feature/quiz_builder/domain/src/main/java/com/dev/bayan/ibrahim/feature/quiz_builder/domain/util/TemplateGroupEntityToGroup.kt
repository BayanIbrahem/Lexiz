package com.dev.bayan.ibrahim.feature.quiz_builder.domain.util

import com.dev.bayan.ibrahim.core.database.entities.relation.filter.GroupCrossFilterRelation
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import kotlinx.collections.immutable.toPersistentList

class TemplateGroupEntityToGroup {
}

fun List<GroupCrossFilterRelation>.asFilterGroupList(): List<FilterGroup> =
    map { (group, filters) ->
        FilterGroup(
            id = group.id,
            name = group.name.asDynamicString(),
            filters = filters.asFilterList().toPersistentList(),
            saveStatus = BuilderItemSaveStatus.SAVED,
        )
    }