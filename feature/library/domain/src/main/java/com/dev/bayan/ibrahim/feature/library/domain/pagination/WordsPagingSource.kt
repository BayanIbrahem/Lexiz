package com.dev.bayan.ibrahim.feature.library.domain.pagination

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy.*
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.data.repo.JaArCurrentLocaleRepo
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.CategoryWithWordsTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.WordWithCategoriesTypeRelation
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.get.LibraryDbWordsGetRepo
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.simpleString
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.asCategoryItem
import com.dev.bayan.ibrahim.feature.library.data.util.getLanguageItem
import com.dev.bayan.ibrahim.feature.library.domain.model.words.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

class WordsPagingSource(
    private val dataSource: LibraryDbWordsGetRepo,
    private val locale: JaArCurrentLocaleRepo,
    private val unselectedLanguages: Set<String> = setOf(),
    private val unselectedTypes: Set<Long> = setOf(),
    private val unselectedCategories: Set<Long> = setOf(),
    private val groupBy: WordsGroupBy = LANGUAGE,
    private val sortBy: WordsGroupSortBy = WordsGroupSortBy.WORD,
    private val reverseSort: Boolean = false,
) : PagingSource<Int, Word>() {
    override fun getRefreshKey(state: PagingState<Int, Word>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Word> {
        val page = params.key ?: 0
        return try {
            (when (groupBy) {
                CATEGORY -> {
                    dataSource.contentWordCategoryWithWordsType(
                        limit = params.loadSize,
                        offset = page * params.loadSize,
                        unselectedLanguages = unselectedLanguages,
                        unselectedTypes = unselectedTypes,
                        unselectedCategories = unselectedCategories,
                        sortBy = sortBy,
                        reverseSort = reverseSort
                    ).map { list: List<CategoryWithWordsTypeRelation> ->
//                  warning heavy load log
//                  Log.d("load_words", "categories flow page: ${list.map { it.simpleString() }}")
                        list.map { relation ->
                            val category = relation.category.name
                            val categoryId = relation.category.id
                            relation.words.map {
                                it.asWord(
                                    groupName = category,
                                    groupId = categoryId,
                                    getLanguageItem = locale::getLanguageItem,
                                )
                            }
                        }.flatten()
                    }
                }

                else -> {
                    dataSource.contentWordWordWithCategoriesType(
                        limit = params.loadSize,
                        offset = page * params.loadSize,
                        unselectedLanguages = unselectedLanguages,
                        unselectedTypes = unselectedTypes,
                        unselectedCategories = unselectedCategories,
                        groupBy = groupBy,
                        sortBy = sortBy,
                        reverseSort = reverseSort,
                    ).map { list: List<WordWithCategoriesTypeRelation> ->
//                  warning heavy load log
//                  Log.d("load_words", "normal flow page: ${list.map { it.simpleString() }}")
                        list.map { relation ->
                            val languageSelfName = relation.wordWithType.word.language_code.run {
                                locale.getLanguageSelfDisplayName(this)
                            }
                            relation.asWord(
                                groupName = when (groupBy) {
                                    LANGUAGE -> languageSelfName
                                    MEANING -> relation.wordWithType.word.meaningId.toString()
                                    TYPE -> "$languageSelfName:${relation.wordWithType.typeName}"
                                    NONE -> null
                                    else -> null
                                },
                                groupId = when (groupBy) {
                                    LANGUAGE -> relation.wordWithType.word.language_code.hashCode()
                                        .toLong()

                                    MEANING -> relation.wordWithType.word.meaningId
                                    TYPE -> relation.wordWithType.word.typeId
                                    NONE -> INVALID_ID
                                    else -> INVALID_ID
                                },
                                getLanguageItem = locale::getLanguageItem,
                            )
                        }
                    }
                }
            }).first().run {
                LoadResult.Page(
                    data = this,
                    prevKey = if (page == 0) null else page.dec(),
                    nextKey = if (this.isEmpty()) null else page.inc(),
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    companion object {
        fun buildPagingDataFlow(
            pageSize: Int = 100,
            dataSource: LibraryDbWordsGetRepo,
            locale: JaArCurrentLocaleRepo,
            unselectedLanguages: Set<String> = setOf(),
            unselectedTypes: Set<Long> = setOf(),
            unselectedCategories: Set<Long> = setOf(),
            groupBy: WordsGroupBy = LANGUAGE,
            sortBy: WordsGroupSortBy = WordsGroupSortBy.WORD,
            reverseSort: Boolean = false,
        ): Flow<PagingData<Word>> = Pager(
            PagingConfig(
                pageSize = pageSize,
            )
        ) {
            WordsPagingSource(
                dataSource = dataSource,
                locale = locale,
                unselectedLanguages = unselectedLanguages,
                unselectedTypes = unselectedTypes,
                unselectedCategories = unselectedCategories,
                groupBy = groupBy,
                sortBy = sortBy,
                reverseSort = reverseSort,
            )
        }.flow
    }
}

private fun WordWithCategoriesTypeRelation.asWord(
    groupName: String?,
    groupId: Long,
    getLanguageItem: (code: String) -> LanguageItem,
): Word {
    return Word(
        groupId = groupId,
        groupName = groupName,
        wordId = wordWithType.word.id!!,
        name = wordWithType.word.name,
        description = wordWithType.word.description,
        language = getLanguageItem(wordWithType.word.language_code),
        meaningId = wordWithType.word.meaningId,
        categories = categories.map { it.asCategoryItem() },
        type = TypeItem(
            id = wordWithType.word.typeId,
            languageCode = wordWithType.word.language_code,
            name = wordWithType.typeName,
            description = ""
        ),
        addDate = Instant.fromEpochMilliseconds(wordWithType.word.addDate),
        lastEditDate = Instant.fromEpochMilliseconds(wordWithType.word.lastEditDate),
    )
}

