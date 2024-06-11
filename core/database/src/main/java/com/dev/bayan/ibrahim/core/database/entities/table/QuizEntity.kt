package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.QUIZ
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.QUIZ.DEF_VALUES

/**
 * jaar database
 * this table stores quizzes
 * @param id id of quiz
 * @param name name of quiz
 *
 * @param is_rule_store_type boolean value check if storing type of this quiz is by rules (it's words
 * updates by type) or by words, so this quiz will contain fixed set of words from the moment of its
 * creation
 * @param parts number of parts in this quiz, by default it is one, if it set to more than one,
 * then each time user apply this quiz he will take next part of this quiz
 * @param last_applied_part last applied bart index (if -1 this this is first applying)
 * @param is_one_time_execution boolean value, if true, then the quiz will be deleted after finishing
 *
 * @param first_language asking language of this quiz
 * @param second_language answering language of this quiz
 * @param max_word_time (in seconds) max available time for each asked word during the quiz
 *
 * @param next_apply_date the date which it will remind user to be applied (nullable)
 * @param last_applied_date last time user took this quiz
 * @param applied_times_count how many times user applied this quiz
 */
@Entity(
    tableName = QUIZ.table,
    // todo quizzes names now is unique
    indices = [
        Index(QUIZ.name, unique = true),
        Index(QUIZ.id, unique = true),
    ]
)

data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = QUIZ.id)
    val id: Long? = null,
    @ColumnInfo(name = QUIZ.name)
    val name: String,

    @ColumnInfo(name = QUIZ.is_rule_store_type, defaultValue = DEF_VALUES.is_rule_store_type)
    val is_rule_store_type: Boolean,
    @ColumnInfo(name = QUIZ.parts, defaultValue = DEF_VALUES.parts)
    val parts: Int,
    @ColumnInfo(name = QUIZ.last_applied_part, defaultValue = DEF_VALUES.last_applied_part)
    val last_applied_part: Int,
    @ColumnInfo(name = QUIZ.is_one_time_execution, defaultValue = DEF_VALUES.is_one_time_execution)
    val is_one_time_execution: Boolean,

    @ColumnInfo(name = QUIZ.first_language)
    val first_language: String,
    @ColumnInfo(name = QUIZ.second_language)
    val second_language: String,
    @ColumnInfo(name = QUIZ.max_word_time, defaultValue = DEF_VALUES.max_word_time)
    val max_word_time: Long,

    @ColumnInfo(name = QUIZ.next_apply_date, defaultValue = DEF_VALUES.next_apply_date)
    val next_apply_date: Long?,
    @ColumnInfo(name = QUIZ.last_apply_date, defaultValue = DEF_VALUES.last_apply_date)
    val last_applied_date: Long?,
    @ColumnInfo(name = QUIZ.applied_times_count, defaultValue = DEF_VALUES.applied_times_count)
    val applied_times_count: Int,
)