package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbInternalApi
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbPublicApi
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.CategoryWithWordsRelation
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.CategoryNameId
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD_CROSS_CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {
    @JaArDbInternalApi
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryEntity): Long?

    @JaArDbInternalApi
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<CategoryEntity>): List<Long>

    @JaArDbPublicApi
    @Update
    suspend fun updateCategory(category: CategoryEntity): Int

    @JaArDbInternalApi
    @Query(
        "DELETE FROM ${WORD_CROSS_CATEGORY.table} " +
                "WHERE ${WORD_CROSS_CATEGORY.category_id} = :categoryId AND " +
                "${WORD_CROSS_CATEGORY.word_id} NOT IN (:newWordsSet)"
    )
    suspend fun deleteCategoryExtraWords(categoryId: Long, newWordsSet: Set<Long>): Int

    @JaArDbPublicApi
    @Query("DELETE FROM ${CATEGORY.table} WHERE ${CATEGORY.id} IN (:categoryIds)")
    suspend fun deleteCategories(categoryIds: Set<Long>): Int

    @JaArDbPublicApi
    @Query("SELECT * FROM ${CATEGORY.table} WHERE ${CATEGORY.id} = :id")
    fun getCategory(id: Long): Flow<CategoryEntity?>

    @JaArDbPublicApi
    @Transaction
    @Query("SELECT * FROM ${CATEGORY.table} WHERE ${CATEGORY.id} = :id")
    fun getCategoryWithWords(id: Long): Flow<CategoryWithWordsRelation?>

    @JaArDbPublicApi
    @Query("SELECT MAX(${CATEGORY.last_edit_date}) FROM ${CATEGORY.table}")
    fun getCategoryLastEditDate(): Flow<Long?>

    @JaArDbPublicApi
    @Query("SELECT * FROM ${CATEGORY.table}")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @JaArDbPublicApi
    @Query("SELECT ${CATEGORY.id}, ${CATEGORY.name} FROM ${CATEGORY.table}")
    fun getAllCategoriesNames(): Flow<List<CategoryNameId>>

    @JaArDbPublicApi
    @Transaction
    @Query("SELECT * FROM ${CATEGORY.table}")
    fun getAllCategoriesWithWords(): Flow<List<CategoryWithWordsRelation>>

    @JaArDbPublicApi
    @Query(
        """
         SELECT ${WORD_CROSS_CATEGORY.category_id} 
         FROM ${WORD_CROSS_CATEGORY.table}
         WHERE ${WORD_CROSS_CATEGORY.word_id} IN (
            SELECT ${DB_NAMES.WORD.id} FROM ${DB_NAMES.WORD.table}
            WHERE ${DB_NAMES.WORD.language_code} = :languageCode
         )
      """
    )
    fun getCategoriesIdsOfLanguage(languageCode: String): Flow<List<Long>>


    @JaArDbPublicApi
    @Query(
        """
            SELECT
                ${CATEGORY.id},
                ${CATEGORY.name}
            FROM
                ${CATEGORY.table}
            WHERE ${CATEGORY.id} IN (
                SELECT
                    ${WORD_CROSS_CATEGORY.category_id}
                FROM
                    ${WORD_CROSS_CATEGORY.table}
            )
        """
    )
    fun getAllUsedCategoriesNames(): Flow<List<CategoryNameId>>
}
