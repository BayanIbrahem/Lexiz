package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.save_new

import com.dev.bayan.ibrahim.core.common.model.SavableCategoryItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.save_new.LibraryDbEditorSaveNewCategoryRepo
import javax.inject.Inject

class LibraryDbEditorSaveNewCategoryUseCase @Inject constructor(
    private val repo: LibraryDbEditorSaveNewCategoryRepo,
) {
    suspend operator fun invoke(
        category: SavableCategoryItem
    ): Long? {
        return repo.editorSaveNewCategory(
            name = category.name,
            description = category.description,
            initWords = category.initWords,
        )
    }
}