package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.suggestion

import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbEditorRepo
import kotlinx.coroutines.flow.Flow

/**
 * this is part of [LibraryDbEditorRepo] this provide functionality to get suggestions of word input
 * fields while typing
 */
interface LibraryDbEditorSuggestionRepo {

    /**
     * returns all words names used for suggestions
     */
    fun editorSuggestAllWordsWithNames(): Flow<List<WordEntity>>

    /**
     * return all available languages for suggestions
     */
    fun editorSuggestAllLanguages(): Flow<List<LanguageEntity>>

    /**
     * return all categories names for suggestions (in every language)
     */
    fun editorSuggestAllCategories(): Flow<List<CategoryEntity>>

    /**
     * return all types names for suggestions (in every language)
     */
    fun editorSuggestAllTypesOfLanguage(languageCode: String): Flow<List<TypeEntity>>


}