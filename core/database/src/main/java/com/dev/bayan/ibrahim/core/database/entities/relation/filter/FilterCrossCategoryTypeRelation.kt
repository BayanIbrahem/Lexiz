package com.dev.bayan.ibrahim.core.database.entities.relation.filter

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossTypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES


data class FilterCrossCategoryTypeRelation(
    @Embedded
    val filter: FilterEntity,
    @Relation(
        parentColumn = DB_NAMES.FILTER.id,
        entityColumn = DB_NAMES.CATEGORY.id,
        entity = CategoryEntity::class,
        associateBy = Junction(
            value = FilterCrossCategoryEntity::class,
            parentColumn = DB_NAMES.FILTER_CROSS_CATEGORY.filter_id,
            entityColumn = DB_NAMES.FILTER_CROSS_CATEGORY.category_id,
        )
    )
    val categories: List<CategoryEntity>,
    @Relation(
        parentColumn = DB_NAMES.FILTER.id,
        entityColumn = DB_NAMES.TYPE.id,
        entity = TypeEntity::class,
        associateBy = Junction(
            value = FilterCrossTypeEntity::class,
            parentColumn = DB_NAMES.FILTER_CROSS_TYPE.filter_id,
            entityColumn = DB_NAMES.FILTER_CROSS_TYPE.type_id,
        )
    )
    val types: List<TypeEntity>,
)
