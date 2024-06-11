package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.MEANING

/**
 * ja ar database meanings table
 * this table stores meanings of the words,
 * each meaning has multi categories (also each category has multi meanings)
 * @param id id of meaning
 */
@Entity(tableName = MEANING.table)
data class MeaningEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MEANING.id)
    val id: Long? = null
)
