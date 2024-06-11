package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.init

import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.CategoryWithWordsRelation
import kotlinx.coroutines.flow.Flow


interface LibraryDbEditorInitCategoryRepo {
    fun editorGetCategoryWithWords(categoryId: Long): Flow<CategoryWithWordsRelation?>
}