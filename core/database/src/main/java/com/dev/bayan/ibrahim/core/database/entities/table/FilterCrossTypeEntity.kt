package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.TYPE
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.FILTER_CROSS_TYPE

/**
 * jaar database
 * this column stores n-to-n relation between filters and types.
 * @param type_id type id
 * @param filter_id filter group id
 * @see TypeEntity
 * @see FilterGroupEntity
 */
@Entity(
    tableName = FILTER_CROSS_TYPE.table,
    primaryKeys = [
        FILTER_CROSS_TYPE.type_id,
        FILTER_CROSS_TYPE.filter_id,
    ],
    indices = [
        Index(FILTER_CROSS_TYPE.type_id, unique = false),
        Index(FILTER_CROSS_TYPE.filter_id, unique = false),
    ],
    foreignKeys = [
        ForeignKey(
            entity = TypeEntity::class,
            parentColumns = [TYPE.id],
            childColumns = [FILTER_CROSS_TYPE.type_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = FilterEntity::class,
            parentColumns = [DB_NAMES.FILTER.id],
            childColumns = [FILTER_CROSS_TYPE.filter_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class FilterCrossTypeEntity(
    @ColumnInfo(name = FILTER_CROSS_TYPE.type_id)
    val type_id: Long,
    @ColumnInfo(name = FILTER_CROSS_TYPE.filter_id)
    val filter_id: Long,
)
