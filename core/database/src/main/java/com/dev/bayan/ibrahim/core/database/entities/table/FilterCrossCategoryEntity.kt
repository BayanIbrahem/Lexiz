package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.FILTER_CROSS_CATEGORY

/**
 * jaar database
 * this column stores n-to-n relation between filters and categories.
 * @param category_id category id
 * @param filter_id filter group id
 * @see CategoryEntity
 * @see FilterGroupEntity
 */
@Entity(
    tableName = FILTER_CROSS_CATEGORY.table,
    indices = [
        Index(FILTER_CROSS_CATEGORY.category_id, unique = false),
        Index(FILTER_CROSS_CATEGORY.filter_id, unique = false)
    ],
    primaryKeys = [
        FILTER_CROSS_CATEGORY.category_id,
        FILTER_CROSS_CATEGORY.filter_id,
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = [CATEGORY.id],
            childColumns = [FILTER_CROSS_CATEGORY.category_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = FilterEntity::class,
            parentColumns = [DB_NAMES.FILTER.id],
            childColumns = [FILTER_CROSS_CATEGORY.filter_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class FilterCrossCategoryEntity(
    @ColumnInfo(name = FILTER_CROSS_CATEGORY.category_id)
    val category_id: Long,
    @ColumnInfo(name = FILTER_CROSS_CATEGORY.filter_id)
    val filter_id: Long,
)
