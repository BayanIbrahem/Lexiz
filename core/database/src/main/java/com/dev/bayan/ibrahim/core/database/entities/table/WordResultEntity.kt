package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.RESULT
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD_RESULT

/**
 * jaar database, this table store a specific result of a word
 * @param result_id id of result
 * @param word_id id of word
 * @param is_failed if the result failed or not
 * @see WordEntity
 * @see ResultEntity
 */
@Entity(
    tableName = WORD_RESULT.table,
    indices = [
        Index(WORD_RESULT.word_id, unique = false),
        Index(WORD_RESULT.result_id, unique = false),
    ],
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = [WORD.id],
            childColumns = [WORD_RESULT.word_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = ResultEntity::class,
            parentColumns = [RESULT.id],
            childColumns = [WORD_RESULT.result_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class WordResultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WORD_RESULT.id)
    val id: Long? = null,
    @ColumnInfo(name = WORD_RESULT.result_id)
    val result_id: Long,
    @ColumnInfo(name = WORD_RESULT.word_id)
    val word_id: Long,
    @ColumnInfo(name = WORD_RESULT.is_failed)
    val is_failed: Boolean,
)
