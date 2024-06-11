package com.dev.bayan.ibrahim.feature.library.data.implementaion

import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputCategoryEditNullIdException
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputMissingFieldException
import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputWordEditNullIdException
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.database.dao.*
import com.dev.bayan.ibrahim.core.database.dao.SafeWordDao
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.CategoryWithWordsRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.WordWithCategoriesRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.CategoryWithWordsTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.WordWithCategoriesTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.CategoryNameId
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.TypeNameLanguageId
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.core.database.utils.columnName
import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.jvm.Throws

class LibraryDbRepoImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val languageDao: LanguageDao,
    private val typeDao: TypeDao,
    private val wordDao: WordDao,
    private val safeWordDao: SafeWordDao,
) : LibraryDbRepo {
    override fun getWordLastEditDate(): Flow<Long?> = wordDao.getWordLastEditDate()

    override fun contentWordGetAllUsedLanguages(): Flow<List<LanguageItem>> = languageDao
        .getAllNonEmptyLanguages().map { list ->
            list.map {
                LanguageItem(
                    languageCode = it.language_code,
                    localDisplayName = getLanguageLocalDisplayName(it.language_code),
                    selfDisplayName = getLanguageSelfDisplayName(it.language_code),
                )
            }
        }

    override fun contentWordGetAllUsedCategories(): Flow<List<CategoryNameId>> =
        categoryDao.getAllUsedCategoriesNames()

    override fun contentWordGetAllUsedTypes(): Flow<List<TypeNameLanguageId>> =
        typeDao.getAllUsedTypesNames()

    override fun contentWordWordWithCategoriesType(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        groupBy: WordsGroupBy,
        sortBy: WordsGroupSortBy,
        reverseSort: Boolean,
    ): Flow<List<WordWithCategoriesTypeRelation>> = if (reverseSort) {
        wordDao.getPaginatedWordsWithCategoriesTypesDescSort(
            limit = limit,
            offset = offset,
            unselectedLanguages = unselectedLanguages,
            unselectedTypes = unselectedTypes,
            unselectedCategories = unselectedCategories,
            groupBy = groupBy.columnName,
            sortBy = sortBy.columnName,
        )
    } else {
        wordDao.getPaginatedWordsWithCategoriesTypesAscSort(
            limit = limit,
            offset = offset,
            unselectedLanguages = unselectedLanguages,
            unselectedTypes = unselectedTypes,
            unselectedCategories = unselectedCategories,
            groupBy = groupBy.columnName,
            sortBy = sortBy.columnName,
        )
    }

    override fun contentWordCategoryWithWordsType(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        sortBy: WordsGroupSortBy,
        reverseSort: Boolean
    ): Flow<List<CategoryWithWordsTypeRelation>> = if (!reverseSort) {
        wordDao.getPaginatedCategoriesWithWordTypeAscSort(
            limit = limit,
            offset = offset,
            unselectedLanguages = unselectedLanguages,
            unselectedTypes = unselectedTypes,
            unselectedCategories = unselectedCategories,
            sortBy = sortBy.columnName,
        )
    } else {
        wordDao.getPaginatedCategoriesWithWordTypeDescSort(
            limit = limit,
            offset = offset,
            unselectedLanguages = unselectedLanguages,
            unselectedTypes = unselectedTypes,
            unselectedCategories = unselectedCategories,
            sortBy = sortBy.columnName,
        )
    }

    override suspend fun contentDeleteWords(ids: Set<Long>): Int {
        return wordDao.deleteWords(ids)
    }

    override fun editorGetWordWithCategories(wordId: Long): Flow<WordWithCategoriesRelation?> {
        return wordDao.getWordWithCategories(wordId)
    }

    override fun editorGetWordType(wordId: Long): Flow<TypeEntity> {
        return typeDao.getTypeOfWord(wordId)
    }

    override fun editorGetWordMeaningFirstWord(wordId: Long): Flow<WordEntity?> {
        return wordDao.getSameMeaningWords(wordId).map { it.firstOrNull() }
    }

    override fun editorGetCategoryWithWords(
        categoryId: Long,
    ): Flow<CategoryWithWordsRelation?> {
        return categoryDao.getCategoryWithWords(categoryId)
    }

    override fun editorSuggestAllWordsWithNames(): Flow<List<WordEntity>> {
        return wordDao.getAllWordsWithNames()
    }

    override fun editorSuggestAllLanguages(): Flow<List<LanguageEntity>> {
        return languageDao.getAllLanguages()
    }

    override fun editorSuggestAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }

    override fun editorSuggestAllTypesOfLanguage(languageCode: String): Flow<List<TypeEntity>> {
        return typeDao.getAllTypesOfLanguage(languageCode)
    }

    /**
     * @throws JaArDbInputMissingFieldException
     * if name and description of the word are null [JaArDbInputWordNameDescriptionException]
     * or any category data is invalid [JaArDbInputWordCategoryException]
     */
    @Throws(JaArDbInputMissingFieldException::class)
    override suspend fun editorSaveNewWord(
        name: String,
        description: String?,
        languageCode: String,
        typeId: Long,
        categories: Set<Long>,
        meaningId: Long?,
    ): Long {
        return safeWordDao.safeWordInsertTransaction(
            name = name,
            description = description,
            languageCode = languageCode,
            categories = categories,
            meaningId = meaningId,
            typeId = typeId,
        )
    }

    /**
     * @throws JaArDbInputMissingFieldException
     * if name and description of the word are null [JaArDbInputWordNameDescriptionException]
     * or any category data is invalid [JaArDbInputWordCategoryException]
     */
    @Throws(JaArDbInputMissingFieldException::class)
    override suspend fun editorSaveEditedWord(
        id: Long,
        name: String,
        description: String?,
        languageCode: String,
        typeId: Long,
        categories: Set<Long>,
        meaningId: Long?,
    ): Boolean {
        val oldWord =
            wordDao.getWord(id).firstOrNull() ?: throw JaArDbInputWordEditNullIdException()
        return safeWordDao.safeWordUpdateTransaction(
            oldWord = oldWord,
            name = name,
            description = description,
            languageCode = languageCode,
            categories = categories,
            meaningId = meaningId,
            typeId = typeId
        )
    }

    override suspend fun editorSaveNewCategory(
        name: String,
        description: String?,
        initWords: Set<Long>,
    ): Long? {
        val categoryId = categoryDao.insertCategory(
            CategoryEntity(
                name = name,
                description = description,
                add_date = System.currentTimeMillis(),
                last_edit_date = System.currentTimeMillis(),
            )
        )
        if (categoryId != null) {
            wordDao.insertAllWordCrossCategory(
                initWords.map { WordCrossCategoryEntity(it, categoryId) }
            )
        }
        return categoryId
    }

    override suspend fun editorSaveExistedCategory(
        id: Long,
        name: String,
        description: String?,
        newWordsSet: Set<Long>,
    ): Boolean {
        val oldCategory =
            categoryDao.getCategory(id).firstOrNull()
                ?: throw JaArDbInputCategoryEditNullIdException()
        categoryDao.deleteCategoryExtraWords(oldCategory.id!!, newWordsSet)
        return categoryDao.updateCategory(
            oldCategory.copy(name = name, description = description)
        ) == 1
    }
}