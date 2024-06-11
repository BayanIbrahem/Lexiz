package com.dev.bayan.ibrahim.core.database.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbPublicApi
import com.dev.bayan.ibrahim.core.database.entities.table.MeaningEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity

@Dao
interface SafeWordDao : WordDao, LanguageDao, CategoryDao, MeaningDao, TypeDao {
    @JaArDbPublicApi
    @Transaction
    suspend fun safeWordInsertTransaction(
        name: String,
        description: String?,
        languageCode: String,
        typeId: Long,
        categories: Set<Long>,
        meaningId: Long?,
    ): Long {
        // insert meaning if new:
        val meaningSafeId = meaningId ?: insertMeaning(MeaningEntity())

        // get all categories ids (insert new categories)

        val wordEntity = WordEntity(
            name = name,
            description = description,
            language_code = languageCode,
            meaningId = meaningSafeId,
            typeId = typeId,
            addDate = System.currentTimeMillis(),
        )
        return insertWord(wordEntity).also { wordId ->
            insertAllWordCrossCategory(
                categories.map { categoryId ->
                    WordCrossCategoryEntity(wordId, categoryId)
                }
            )
        }
    }

    @JaArDbPublicApi
    @Transaction
    suspend fun safeWordUpdateTransaction(
        oldWord: WordEntity,
        name: String,
        description: String?,
        languageCode: String,
        categories: Set<Long>,
        meaningId: Long?,
        typeId: Long,
    ): Boolean {
        // insert meaning if new:
        val meaningSafeId = meaningId ?: insertMeaning(MeaningEntity())
        val oldMeaning = oldWord.meaningId

        insertAllWordCrossCategory(
            categories.map { WordCrossCategoryEntity(oldWord.id!!, it) }
        )
        deleteWordExtraCategories(oldWord.id!!, categories)

        val newWord = oldWord.copy(
            name = name,
            description = description,
            language_code = languageCode,
            meaningId = meaningSafeId,
            typeId = typeId,
            lastEditDate = System.currentTimeMillis(),
        )
        return (updateWord(newWord) == 1).also {
            deletePossibleEmptyMeaning(oldMeaning)
        }
    }
}