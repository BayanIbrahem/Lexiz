package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.init

import com.dev.bayan.ibrahim.core.common.model.LoadableCategoryItem
import com.dev.bayan.ibrahim.core.database.entities.table.asWordItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.init.LibraryDbEditorInitCategoryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LibraryDbEditorInitCategoryUseCase @Inject constructor(
    private val repo: LibraryDbEditorInitCategoryRepo,
) {
    suspend operator fun invoke(categoryId: Long): LoadableCategoryItem? {
        return repo.editorGetCategoryWithWords(categoryId).firstOrNull()?.run {
            LoadableCategoryItem(
                id = categoryId,
                name = category.name,
                description = category.description,
                initWords = words.map {
                    it.asWordItem()
                },
            )
        }
    }
}