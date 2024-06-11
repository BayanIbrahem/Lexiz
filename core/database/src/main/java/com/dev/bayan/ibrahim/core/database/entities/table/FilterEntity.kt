package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES

/**
 * jaar db
 * store every filter used, either if it a template ([template] is true) or used in a filter group
 * @param id id of the filter,
 * @param name name of the filter,
 *
 * @param primary_value main value of the filter according to its type, if the filter type is single
 * or multi choice then all values will be null
 * @param secondary_value second value of the filter, not all filters has this value,
 * @param tertiary_value an extra optional value may some filters have
 *
 * @param template if this is a template filter (showed when we want to choose one of) this this is
 * true, otherwise this will be false
 */
@Entity(tableName = DB_NAMES.FILTER.table)
data class FilterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DB_NAMES.FILTER.id)
    val id: Long? = null,

    @ColumnInfo(name = DB_NAMES.FILTER.name)
    val name: String,

    @ColumnInfo(name = DB_NAMES.FILTER.type)
    val type: Int,

    @ColumnInfo(name = DB_NAMES.FILTER.primary_value)
    val primary_value: Float?,
    @ColumnInfo(name = DB_NAMES.FILTER.secondary_value)
    val secondary_value: Float? = null,
    @ColumnInfo(name = DB_NAMES.FILTER.tertiary_value)
    val tertiary_value: Float? = null,

    @ColumnInfo(name = DB_NAMES.FILTER.template)
    val template: Boolean,
)
