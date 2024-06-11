@file:Suppress("UNCHECKED_CAST")

package com.dev.bayan.ibrahim.feature.library.domain.use_case.words

import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.CategoryNameId
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.TypeNameLanguageId
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.asCategoryItem
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.asTypeItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.get.LibraryDbWordsGetBaseDataSetRepo
import com.dev.bayan.ibrahim.feature.library.domain.model.words.WordBaseDataSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * use case for intro screen, which returns update data of last edit time of each content type
 */
class LibraryWordsBaseDataSetCase @Inject constructor(
    private val repo: LibraryDbWordsGetBaseDataSetRepo,
) {
    operator fun invoke(): Flow<WordBaseDataSet> = combine(
        repo.contentWordGetAllUsedLanguages(),
        repo.contentWordGetAllUsedTypes(),
        repo.contentWordGetAllUsedCategories(),
    ) { (l, t, c) ->
        val languages = l as List<LanguageItem>
        val types = (t as List<TypeNameLanguageId>).map { it.asTypeItem() }
        val categories = (c as List<CategoryNameId>).map { it.asCategoryItem() }
        WordBaseDataSet(
            allLanguages = languages,
            allTypes = types, // todo, replace triple with TypeItem
            allCategories = categories,
        )
    }
}

