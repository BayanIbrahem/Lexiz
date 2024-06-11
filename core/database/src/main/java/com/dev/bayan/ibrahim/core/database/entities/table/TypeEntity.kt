package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.TYPE

/**
 * this information will not be inserted manually
 * @param id id of the type
 * @param name
 * @param description
 * @param language_code
 * @see [WordEntity]
 */
@Entity(tableName = TYPE.table)
data class TypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = TYPE.id)
    val id: Long,
    @ColumnInfo(name = TYPE.name)
    val name: String,
    @ColumnInfo(name = TYPE.description)
    val description: String,
    @ColumnInfo(name = TYPE.language)
    val language_code: String,
    @ColumnInfo(name = TYPE.type_version)
    val type_version: Int,
)

