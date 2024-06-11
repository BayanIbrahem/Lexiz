package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.update

interface LibraryDbEditorSaveUpdateCategoryRepo {
    suspend fun editorSaveExistedCategory(
        id: Long,
        name: String,
        description: String?,
        newWordsSet: Set<Long>,
    ): Boolean

}