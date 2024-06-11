package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.QUIZ
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.QUIZ_CROSS_WORD

/**
 * jaar database
 * this table represent then n-to-n relation between quizzes and words
 * when a quiz has fixed words count "not based on rules" then each quiz stores words in it
 * (quiz contains many words in it, and in the same time may the word belong to more than one quiz)
 */
@Entity(
    tableName = QUIZ_CROSS_WORD.table,
    indices = [
        Index(QUIZ_CROSS_WORD.quiz_id, unique = false),
        Index(QUIZ_CROSS_WORD.word_id, unique = false),
    ],
    primaryKeys = [
        QUIZ_CROSS_WORD.quiz_id,
        QUIZ_CROSS_WORD.word_id,
    ],
    foreignKeys = [
        ForeignKey(
            entity = QuizEntity::class,
            parentColumns = [QUIZ.id],
            childColumns = [QUIZ_CROSS_WORD.quiz_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = [WORD.id],
            childColumns = [QUIZ_CROSS_WORD.word_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class QuizCrossWordEntity(
    @ColumnInfo(name = QUIZ_CROSS_WORD.quiz_id)
    val quiz_id: Long,
    @ColumnInfo(name = QUIZ_CROSS_WORD.word_id)
    val word_id: Long,
)