package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.database.entities.relation.quiz.QuizCrossFilterWordsRelation
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query(
        """
         SELECT *
         FROM ${DB_NAMES.QUIZ.table}
      """
    )
    fun getAllTemplateQuizzes(): Flow<List<QuizCrossFilterWordsRelation>>

    @Query(
        """
         SELECT count(*)
         FROM ${DB_NAMES.QUIZ.table}
      """
    )
    fun getQuizzesCount(): Flow<Int>
}
