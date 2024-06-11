package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.update

import com.dev.bayan.ibrahim.core.common.model.SavableCategoryItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.update.LibraryDbEditorSaveUpdateCategoryRepo
import javax.inject.Inject

class LibraryDbEditorSaveUpdateCategoryUseCase @Inject constructor(
    private val repo: LibraryDbEditorSaveUpdateCategoryRepo,
) {
    suspend operator fun invoke(
        category: SavableCategoryItem
    ): Boolean {
        return repo.editorSaveExistedCategory(
            id = category.id,
            name = category.name,
            description = category.description,
            newWordsSet = category.initWords,
        )
    }
}