package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES

/**
 * @param group_id group id
 * @param filter_id filter id
 */
@Entity(
    tableName = DB_NAMES.FILTER_CROSS_GROUP.table,
    indices = [
        Index(DB_NAMES.FILTER_CROSS_GROUP.filter_id, unique = false),
        Index(DB_NAMES.FILTER_CROSS_GROUP.group_id, unique = false),
    ],
    primaryKeys = [
        DB_NAMES.FILTER_CROSS_GROUP.filter_id,
        DB_NAMES.FILTER_CROSS_GROUP.group_id,
    ],
    foreignKeys = [
        ForeignKey(
            entity = FilterEntity::class,
            parentColumns = [DB_NAMES.FILTER.id],
            childColumns = [DB_NAMES.FILTER_CROSS_GROUP.filter_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = FilterGroupEntity::class,
            parentColumns = [DB_NAMES.FILTER_GROUP.id],
            childColumns = [DB_NAMES.FILTER_CROSS_GROUP.group_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ]
)
data class GroupCrossFilterEntity(
    @ColumnInfo(name = DB_NAMES.FILTER_CROSS_GROUP.group_id)
    val group_id: Long,
    @ColumnInfo(name = DB_NAMES.FILTER_CROSS_GROUP.filter_id)
    val filter_id: Long,
)