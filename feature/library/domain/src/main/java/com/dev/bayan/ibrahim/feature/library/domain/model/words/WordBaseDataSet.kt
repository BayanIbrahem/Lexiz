package com.dev.bayan.ibrahim.feature.library.domain.model.words

import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem

/**
 * this represent the base data set which every word is belong to part of it, it is used for filters
 * or selecting from existed data:
 * it store only part of properties in db tables
 * @param allLanguages all languages in the database
 * @param allTypes all types in the data base (id, language, name)
 * @param allCategories categories in the data base
 */
data class WordBaseDataSet(
    val allLanguages: List<LanguageItem> = listOf(),
    val allTypes: List<TypeItem> = listOf(),
    val allCategories: List<CategoryItem> = listOf(),
)