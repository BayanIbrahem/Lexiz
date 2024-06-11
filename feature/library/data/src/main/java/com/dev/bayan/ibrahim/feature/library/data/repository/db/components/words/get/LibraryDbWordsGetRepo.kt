package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.get

import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.CategoryWithWordsTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type.WordWithCategoriesTypeRelation
import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbWordsRepo
import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbRepo
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.lastEditDate.LibraryDbWordLastEditDateRepo
import kotlinx.coroutines.flow.Flow

/**
 * This is a component of [LibraryDbRepo]. This sub-repository provides data for the content screen.
 * part of repository [LibraryDbWordsRepo]
 * (All methods are GETTERS)
 *
 * D) Word:
 * - Type of a word using [contentWordType].
 *
 * @see LibraryDbWordLastEditDateRepo
 *
 * @see LibraryDbWordsRepo
 *
 */


interface LibraryDbWordsGetRepo : LibraryDbWordLastEditDateRepo {
    fun contentWordWordWithCategoriesType(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        groupBy: WordsGroupBy,
        sortBy: WordsGroupSortBy,
        reverseSort: Boolean,
    ): Flow<List<WordWithCategoriesTypeRelation>>


    fun contentWordCategoryWithWordsType(
        limit: Int,
        offset: Int,
        unselectedLanguages: Set<String>,
        unselectedTypes: Set<Long>,
        unselectedCategories: Set<Long>,
        sortBy: WordsGroupSortBy,
        reverseSort: Boolean,
    ): Flow<List<CategoryWithWordsTypeRelation>>
}

