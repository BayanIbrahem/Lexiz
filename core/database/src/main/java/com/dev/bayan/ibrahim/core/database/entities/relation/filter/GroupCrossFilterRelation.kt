package com.dev.bayan.ibrahim.core.database.entities.relation.filter

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity
import com.dev.bayan.ibrahim.core.database.entities.table.GroupCrossFilterEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES

data class GroupCrossFilterRelation(
    @Embedded
    val group: FilterGroupEntity,
    @Relation(
        parentColumn = DB_NAMES.FILTER_GROUP.id,
        entityColumn = DB_NAMES.FILTER.id,
        entity = FilterEntity::class,
        associateBy = Junction(
            value = GroupCrossFilterEntity::class,
            parentColumn = DB_NAMES.FILTER_CROSS_GROUP.group_id,
            entityColumn = DB_NAMES.FILTER_CROSS_GROUP.filter_id,
        )
    )
    val filters: List<FilterCrossCategoryTypeRelation>

)