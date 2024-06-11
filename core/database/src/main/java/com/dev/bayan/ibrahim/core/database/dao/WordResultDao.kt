package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import kotlinx.coroutines.flow.Flow

@Dao
interface WordResultDao {

    //    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(wordResult: WordResultEntity)
//
//    @Update()
//    suspend fun update(wordResult: WordResultEntity)
//
//    @Delete
//    suspend fun delete(wordResult: WordResultEntity)
//
//    @Query("SELECT * FROM ${WORD_RESULT.table}")
//    fun getAll(): Flow<List<WordResultEntity>>
//
//    @Query(
//        "SELECT (SUM(count)/COUNT(count)) FROM (SELECT COUNT(*) as count FROM ${WORD_RESULT.table} " +
//        "WHERE ${WORD_RESULT.is_failed} GROUP BY ${WORD_RESULT.word_id})"
//    )
//    fun getFailedAverage(): Flow<Float>
//
//    @Query(
//        "SELECT (SUM(count)/COUNT(count)) FROM (SELECT COUNT(*) as count FROM ${WORD_RESULT.table} " +
//        "WHERE NOT ${WORD_RESULT.is_failed} GROUP BY ${WORD_RESULT.word_id})"
//    )
//    fun getSucceededAverage(): Flow<Float>
    @Query(
        """
         SELECT MAX(count)
         FROM (
            SELECT COUNT(*) as count
            FROM ${DB_NAMES.WORD_RESULT.table}
            WHERE ${DB_NAMES.WORD_RESULT.is_failed}
            GROUP BY ${DB_NAMES.WORD_RESULT.word_id}
         )        
      """
    )
    fun getMostFailWordCount(): Flow<Int?>

    @Query(
        """
         SELECT MAX(count)
         FROM (
            SELECT COUNT(*) as count
            FROM ${DB_NAMES.WORD_RESULT.table}
            WHERE NOT ${DB_NAMES.WORD_RESULT.is_failed}
            GROUP BY ${DB_NAMES.WORD_RESULT.word_id}
         )        
      """
    )
    fun getMostSuccessWordCount(): Flow<Int?>
}
