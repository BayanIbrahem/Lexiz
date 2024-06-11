package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.get

import com.dev.bayan.ibrahim.core.database.entities.sub_tables.CategoryNameId
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.TypeNameLanguageId
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import kotlinx.coroutines.flow.Flow

interface LibraryDbWordsGetBaseDataSetRepo {
    fun contentWordGetAllUsedLanguages(): Flow<List<LanguageItem>>
    fun contentWordGetAllUsedCategories(): Flow<List<CategoryNameId>>
    fun contentWordGetAllUsedTypes(): Flow<List<TypeNameLanguageId>>
}