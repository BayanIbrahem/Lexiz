package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.annotation.FloatRange
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.RESULT

/**
 * ja ar database,
 * this table stores done tests with words count date and score
 * @param id key of row
 * @param score score percentage
 * @param date date of this quiz result
 * @param quiz_id id of the quiz which has this result
 * @param quiz_name if the quiz is deleted then its name can be used for displaying results
 */
@Entity(
    tableName = RESULT.table,
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = [DB_NAMES.LANGUAGE.code],
            childColumns = [RESULT.first_language],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = [DB_NAMES.LANGUAGE.code],
            childColumns = [RESULT.second_language],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = QuizEntity::class,
            parentColumns = [DB_NAMES.QUIZ.id],
            childColumns = [RESULT.quiz_id],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = QuizEntity::class,
            parentColumns = [DB_NAMES.QUIZ.name],
            childColumns = [RESULT.quiz_name],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RESULT.id)
    val id: Long? = null,
    @FloatRange(0.0, 1.0)
    @ColumnInfo(name = RESULT.score)
    val score: Float,
    @ColumnInfo(name = RESULT.date)
    val date: Long,

    @ColumnInfo(name = RESULT.first_language)
    val first_language: String,
    @ColumnInfo(name = RESULT.second_language)
    val second_language: String,

    @ColumnInfo(name = RESULT.words_count)
    val wordsCont: Int,

    @ColumnInfo(name = RESULT.quiz_id)
    val quiz_id: Long,

    @ColumnInfo(name = RESULT.quiz_name)
    val quiz_name: String,
)
