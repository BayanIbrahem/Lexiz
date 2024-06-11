package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.init

import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.WordWithCategoriesRelation
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import kotlinx.coroutines.flow.Flow
import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbEditorRepo

/**
 * this is part of [LibraryDbEditorRepo] this provide functionality to get initial
 * information's for word when editing
 *
 */

interface LibraryDbEditorInitWordRepo {
    fun editorGetWordWithCategories(wordId: Long): Flow<WordWithCategoriesRelation?>
    fun editorGetWordType(wordId: Long): Flow<TypeEntity>
    fun editorGetWordMeaningFirstWord(wordId: Long): Flow<WordEntity?>
}