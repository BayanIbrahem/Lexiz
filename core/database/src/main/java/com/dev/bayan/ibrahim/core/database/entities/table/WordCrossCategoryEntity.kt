package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD_CROSS_CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY

@Entity(
    tableName = WORD_CROSS_CATEGORY.table,
    primaryKeys = [WORD_CROSS_CATEGORY.word_id, WORD_CROSS_CATEGORY.category_id],
    indices = [
        Index(WORD_CROSS_CATEGORY.word_id, unique = false),
        Index(WORD_CROSS_CATEGORY.category_id, unique = false),
    ],
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = [WORD.id],
            childColumns = [WORD_CROSS_CATEGORY.word_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = [CATEGORY.id],
            childColumns = [WORD_CROSS_CATEGORY.category_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class WordCrossCategoryEntity(
    @ColumnInfo(name = WORD_CROSS_CATEGORY.word_id)
    val word_id: Long,
    @ColumnInfo(name = WORD_CROSS_CATEGORY.category_id)
    val category_id: Long,
)
