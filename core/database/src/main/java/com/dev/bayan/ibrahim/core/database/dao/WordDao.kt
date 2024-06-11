package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbPublicApi
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbInternalApi
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.WordWithCategoriesRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.CategoryWithWordsTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.WordWithCategoriesTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD_CROSS_CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @JaArDbInternalApi
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: WordEntity): Long

    @JaArDbPublicApi
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllWordCrossCategory(wordCrossCategories: List<WordCrossCategoryEntity>)

    /**
     * @suppress don't update any word it may cause conflicts like in foreign keys
     */
    @JaArDbInternalApi
    @Update()
    suspend fun updateWord(word: WordEntity): Int

    @JaArDbPublicApi
    @Query("DELETE FROM ${WORD.table} WHERE ${WORD.id} in (:wordsIds)")
    suspend fun deleteWords(wordsIds: Set<Long>): Int

    @JaArDbPublicApi
    @Query(
        "DELETE FROM ${WORD.table} " +
                "WHERE ${WORD.id} not in (" +
                "SELECT DISTINCT ${WORD_CROSS_CATEGORY.word_id} " +
                "FROM ${WORD_CROSS_CATEGORY.table}" +
                ")"
    )
    suspend fun deleteWordsWithoutCategories(): Int

    /**
     * remove any category is not in the provided categories
     * @suppress using it carelessly will may cause unexpected results, it is called in every needed
     * place in the public apis of the database
     */
    @JaArDbInternalApi
    @Query(
        "DELETE FROM ${WORD_CROSS_CATEGORY.table} " +
                "WHERE ${WORD_CROSS_CATEGORY.word_id} = :wordId AND " +
                "${WORD_CROSS_CATEGORY.category_id} NOT IN (:newCategories)"
    )
    suspend fun deleteWordExtraCategories(wordId: Long, newCategories: Set<Long>)

    @JaArDbPublicApi
    @Query("SELECT * FROM ${WORD.table} WHERE ${WORD.id} = :id")
    fun getWord(id: Long): Flow<WordEntity?>

    @JaArDbPublicApi
    @Query(
        "SELECT ${WORD.meaning_id}, COUNT(${WORD_CROSS_CATEGORY.category_id}) as count FROM ${WORD.table} " +
                "JOIN ${WORD_CROSS_CATEGORY.table} ON ${WORD.id} = ${WORD_CROSS_CATEGORY.word_id} " +
                "GROUP BY ${WORD.meaning_id} "
    )
    fun getMeaningsCategoriesCount(): Flow<Map<@MapColumn(WORD.meaning_id) Long, @MapColumn("count") Int>>

    @JaArDbPublicApi
    @Query(
        "SELECT ${WORD.language_code}, COUNT(${WORD_CROSS_CATEGORY.category_id}) as count FROM ${WORD.table} " +
                "JOIN ${WORD_CROSS_CATEGORY.table} ON ${WORD.id} = ${WORD_CROSS_CATEGORY.word_id} " +
                "GROUP BY ${WORD.language_code} "
    )
    fun getLanguagesCategoriesCount(): Flow<Map<@MapColumn(WORD.language_code) String, @MapColumn("count") Int>>

    @JaArDbPublicApi
    @Query("SELECT MAX(${WORD.last_edit_date}) FROM ${WORD.table}")
    fun getWordLastEditDate(): Flow<Long?>

    @JaArDbPublicApi
    @Query(
        "SELECT * FROM ${WORD.table} " +
                "WHERE ${WORD.id} != :wordId AND ${WORD.meaning_id} = (" +
                "SELECT ${WORD.meaning_id} FROM ${WORD.table} WHERE ${WORD.id} = :wordId" +
                ")"
    )
    fun getSameMeaningWords(wordId: Long): Flow<List<WordEntity>>

    @JaArDbPublicApi
    @Transaction
    @Query("SELECT * FROM ${WORD.table} WHERE ${WORD.id} = :wordId")
    fun getWordWithCategories(wordId: Long): Flow<WordWithCategoriesRelation?>

    @JaArDbPublicApi
    @Transaction
    @Query("SELECT * FROM ${WORD.table}")
    fun getAllWordsWithCategories(): Flow<List<WordWithCategoriesRelation>>

    /**
     * return a page of words grouped by [groupBy] and then sorted by [sortBy] **NORMAL SORT**
     * @param limit page size
     * @param offset start element
     * @param unselectedLanguages list of filtered languages
     * @param unselectedTypes list of filtered types
     * @param unselectedCategories list of filtered categories
     * @param groupBy column which we group by (first sorted by) see [WordsGroupBy]
     * @param sortBy column which we sort by (second sorted by) see [WordsGroupSortBy]
     */
    @JaArDbPublicApi
    @Transaction
    @Query(
        """
        SELECT *
        FROM ${WORD.table}
        WHERE
            (${WORD.language_code} NOT IN (:unselectedLanguages))
            AND (${WORD.type_id} NOT IN (:unselectedTypes))
            AND (NOT EXISTS(
                SELECT *
                FROM ${WORD_CROSS_CATEGORY.table}
                WHERE
                    ${WORD_CROSS_CATEGORY.word_id} = ${WORD.id}
                    AND ${WORD_CROSS_CATEGORY.category_id} IN (:unselectedCategories)
            ))
        ORDER BY :groupBy, :sortBy ASC
        LIMIT :limit
        OFFSET :offset
      """
    )
    fun getPaginatedWordsWithCategoriesTypesAscSort(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        groupBy: String,
        sortBy: String,
    ): Flow<List<WordWithCategoriesTypeRelation>>

    /**
     * return a page of words grouped by [groupBy] and then sorted by [sortBy] **REVERSED SORT**
     * @param limit page size
     * @param offset start element
     * @param unselectedLanguages list of filtered languages
     * @param unselectedTypes list of filtered types
     * @param unselectedCategories list of filtered categories
     * @param groupBy column which we group by (first sorted by) see [WordsGroupBy]
     * @param sortBy column which we sort by (second sorted by) see [WordsGroupSortBy]
     */
    @JaArDbPublicApi
    @Transaction
    @Query(
        """
        SELECT *
        FROM ${WORD.table}
        WHERE
            (${WORD.language_code} NOT IN (:unselectedLanguages))
            AND (${WORD.type_id} NOT IN (:unselectedTypes))
            AND (NOT EXISTS(
                SELECT *
                FROM ${WORD_CROSS_CATEGORY.table}
                WHERE
                    ${WORD_CROSS_CATEGORY.word_id} = ${WORD.id}
                    AND ${WORD_CROSS_CATEGORY.category_id} IN (:unselectedCategories)
            ))
        ORDER BY :groupBy, :sortBy DESC
        LIMIT :limit
        OFFSET :offset
      """
    )
    fun getPaginatedWordsWithCategoriesTypesDescSort(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        groupBy: String,
        sortBy: String,
    ): Flow<List<WordWithCategoriesTypeRelation>>

    @JaArDbPublicApi
    @Transaction
    @Query(
        """
        SELECT DISTINCT
            ${CATEGORY.id},
            ${CATEGORY.name}
        FROM ${CATEGORY.table}
        join ${WORD_CROSS_CATEGORY.table} on ${WORD_CROSS_CATEGORY.category_id} = ${CATEGORY.id}
        join ${WORD.table} on ${WORD.id} = ${WORD_CROSS_CATEGORY.word_id}
        WHERE
            (${WORD.language_code} NOT IN (:unselectedLanguages))
            AND (${WORD.type_id} NOT IN (:unselectedTypes))
            AND (NOT EXISTS(
                SELECT *
                FROM ${WORD_CROSS_CATEGORY.table}
                WHERE
                    ${WORD_CROSS_CATEGORY.word_id} = ${WORD.id}
                    AND ${WORD_CROSS_CATEGORY.category_id} IN (:unselectedCategories)
            ))
        ORDER BY ${CATEGORY.name}, :sortBy ASC
        LIMIT :limit
        OFFSET :offset
      """
    )
    fun getPaginatedCategoriesWithWordTypeAscSort(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        sortBy: String,
    ): Flow<List<CategoryWithWordsTypeRelation>>


    //   SELECT DISTINCT
//   *
//   FROM ${WORD.table}
//   join ${WORD_CROSS_CATEGORY.table} on ${WORD_CROSS_CATEGORY.word_id} = ${WORD.id}
//   join ${CATEGORY.table} on ${CATEGORY.id} = ${WORD_CROSS_CATEGORY.category_id}
//
//   SELECT DISTINCT
//   *
//   FROM ${WORD.table}
//   join ${WORD_CROSS_CATEGORY.table} on ${WORD_CROSS_CATEGORY.word_id} = ${WORD.id}
//   join ${CATEGORY.table} on ${CATEGORY.id} = ${WORD_CROSS_CATEGORY.category_id}
    @JaArDbPublicApi
    @Transaction
    @Query(
        """
        SELECT
            DISTINCT ${CATEGORY.id},
            ${CATEGORY.name}
        FROM ${CATEGORY.table}
        join ${WORD_CROSS_CATEGORY.table} on ${WORD_CROSS_CATEGORY.category_id} = ${CATEGORY.id}
        join ${WORD.table} on ${WORD.id} = ${WORD_CROSS_CATEGORY.word_id}
        WHERE
            (${WORD.language_code} NOT IN (:unselectedLanguages))
            AND (${WORD.type_id} NOT IN (:unselectedTypes))
            AND (NOT EXISTS(
                SELECT *
                FROM ${WORD_CROSS_CATEGORY.table}
                WHERE
                    ${WORD_CROSS_CATEGORY.word_id} = ${WORD.id}
                    AND ${WORD_CROSS_CATEGORY.category_id} IN (:unselectedCategories)
            ))
            
        ORDER BY ${CATEGORY.name}, :sortBy DESC
        LIMIT :limit
        OFFSET :offset
      """
    )
    fun getPaginatedCategoriesWithWordTypeDescSort(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        sortBy: String,
    ): Flow<List<CategoryWithWordsTypeRelation>>

    @JaArDbPublicApi
    @Query("SELECT * FROM ${WORD.table} WHERE ${WORD.name} IS NOT NULL")
    fun getAllWordsWithNames(): Flow<List<WordEntity>>

    @JaArDbPublicApi
    @Query("SELECT * FROM ${WORD.table}")
    fun getAllWords(): Flow<List<WordEntity>>

    @JaArDbPublicApi
    @Query(
        "SELECT ${WORD.language_code}, MAX(${WORD.last_edit_date}) as max " +
                "FROM ${WORD.table} GROUP BY ${WORD.language_code}"
    )
    fun getLanguagesLastEditDate(): Flow<Map<@MapColumn(WORD.language_code) String, @MapColumn("max") Long>>

    // todo ignore ignorable characters
    @JaArDbPublicApi
    @Query(
        """
      SELECT MAX(LENGTH(${WORD.name})) FROM ${WORD.table}
   """
    )
    fun getLongestWordLength(): Flow<Int?>

}
