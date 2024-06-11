package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.FILTER_GROUP

/**
 * jaar data base,
 * this entity stores saved filter groups,
 * @param id id of group
 * @param name display name of group
 * @param template if this group is saved as a template ths this value is true otherwise it will be false
 */
@Entity(
    tableName = FILTER_GROUP.table,
)
class FilterGroupEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FILTER_GROUP.id)
    val id: Long? = null,
    @ColumnInfo(name = FILTER_GROUP.name)
    val name: String,
    @ColumnInfo(name = FILTER_GROUP.template)
    val template: Boolean,
)