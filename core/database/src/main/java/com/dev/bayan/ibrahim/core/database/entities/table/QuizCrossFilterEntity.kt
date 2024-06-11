package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.QUIZ
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.FILTER_GROUP
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.QUIZ_CROSS_FILTER

/**
 * ja ar database
 * this entity store values of each quiz with its filters which is n-to-n relation
 * @param quiz_id id of quiz
 * @param filter_group_id id of filter group
 *
 * @see QuizEntity
 * @see FilterGroupEntity
 */
@Entity(
    tableName = QUIZ_CROSS_FILTER.table,
    indices = [
        Index(QUIZ_CROSS_FILTER.quiz_id, unique = false),
        Index(QUIZ_CROSS_FILTER.filter_group_id, unique = false),
    ],
    primaryKeys = [
        QUIZ_CROSS_FILTER.quiz_id,
        QUIZ_CROSS_FILTER.filter_group_id,
    ],
    foreignKeys = [
        ForeignKey(
            entity = QuizEntity::class,
            parentColumns = [QUIZ.id],
            childColumns = [QUIZ_CROSS_FILTER.quiz_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = FilterGroupEntity::class,
            parentColumns = [FILTER_GROUP.id],
            childColumns = [QUIZ_CROSS_FILTER.filter_group_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class QuizCrossFilterEntity(
    @ColumnInfo(name = QUIZ_CROSS_FILTER.quiz_id)
    val quiz_id: Long,
    @ColumnInfo(name = QUIZ_CROSS_FILTER.filter_group_id)
    val filter_group_id: Long,
)
