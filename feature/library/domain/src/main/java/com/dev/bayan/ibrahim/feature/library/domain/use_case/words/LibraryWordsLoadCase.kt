package com.dev.bayan.ibrahim.feature.library.domain.use_case.words

import androidx.paging.PagingData
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.data.repo.JaArCurrentLocaleRepo
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.get.LibraryDbWordsGetRepo
import com.dev.bayan.ibrahim.feature.library.domain.model.words.Word
import com.dev.bayan.ibrahim.feature.library.domain.pagination.WordsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * use case for intro screen, which returns update data of last edit time of each content type
 */
class LibraryWordsLoadCase @Inject constructor(
    private val repo: LibraryDbWordsGetRepo,
    private val locale: JaArCurrentLocaleRepo
) {
    operator fun invoke(
        pageSize: Int = 100,
        unselectedLanguages: Set<String> = setOf(),
        unselectedTypes: Set<Long> = setOf(),
        unselectedCategories: Set<Long> = setOf(),
        groupBy: WordsGroupBy = WordsGroupBy.LANGUAGE,
        sortBy: WordsGroupSortBy = WordsGroupSortBy.WORD,
        reverseSort: Boolean = false,
    ): Flow<PagingData<Word>> = WordsPagingSource.buildPagingDataFlow(
        pageSize = pageSize,
        dataSource = repo,
        locale = locale,
        unselectedLanguages = unselectedLanguages,
        unselectedTypes = unselectedTypes,
        unselectedCategories = unselectedCategories,
        groupBy = groupBy,
        sortBy = sortBy,
        reverseSort = reverseSort,
    )
}

