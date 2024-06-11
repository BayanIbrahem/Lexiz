package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY

/**
 * @param id category id
 *
 * @param name name of the category
 * @param description description of category (optional)
 *
 * @param add_date adding date of category
 * @param last_edit_date
 * @see [MeaningEntity]
 */
@Entity(tableName = CATEGORY.table)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CATEGORY.id)
    val id: Long? = null,

    @ColumnInfo(name = CATEGORY.name)
    val name: String,
    @ColumnInfo(name = CATEGORY.description)
    val description: String?,

    @ColumnInfo(name = CATEGORY.add_date)
    val add_date: Long,
    @ColumnInfo(name = CATEGORY.last_edit_date)
    val last_edit_date: Long,
)
