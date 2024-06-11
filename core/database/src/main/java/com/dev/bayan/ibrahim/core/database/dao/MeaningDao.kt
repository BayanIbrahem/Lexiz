package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbInternalApi
import com.dev.bayan.ibrahim.core.database.entities.table.MeaningEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.MEANING
import kotlinx.coroutines.flow.Flow


@Dao
interface MeaningDao {

    @JaArDbInternalApi
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeaning(meaning: MeaningEntity): Long

    @JaArDbInternalApi
    @Query("DELETE FROM ${MEANING.table} WHERE ${MEANING.id} IN (:meaningsIds)")
    suspend fun deleteMeanings(meaningsIds: Set<Long>): Int

    @JaArDbInternalApi
    @Query(
        "DELETE FROM ${MEANING.table} WHERE ${MEANING.id} = :meaningId AND " +
                "((SELECT COUNT(*) FROM ${WORD.table} WHERE ${WORD.meaning_id} = :meaningId) = 0)"
    )
    suspend fun deletePossibleEmptyMeaning(meaningId: Long): Int

    @JaArDbInternalApi
    @Query(
        "DELETE FROM ${MEANING.table} " +
                "WHERE ${MEANING.id} NOT IN (" +
                "SELECT ${WORD.meaning_id} " +
                "FROM ${WORD.table}" +
                ")"
    )
    suspend fun deletePossibleEmptyMeanings(): Int

    // for test only
    @JaArDbInternalApi
    @Query("SELECT * FROM ${MEANING.table}")
    fun getAllMeanings(): Flow<List<MeaningEntity>>

}
