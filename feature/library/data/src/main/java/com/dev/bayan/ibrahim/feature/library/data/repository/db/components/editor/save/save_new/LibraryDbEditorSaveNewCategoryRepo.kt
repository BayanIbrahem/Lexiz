package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.save_new


interface LibraryDbEditorSaveNewCategoryRepo {
    suspend fun editorSaveNewCategory(
        name: String,
        description: String?,
        initWords: Set<Long>,
    ): Long?
}