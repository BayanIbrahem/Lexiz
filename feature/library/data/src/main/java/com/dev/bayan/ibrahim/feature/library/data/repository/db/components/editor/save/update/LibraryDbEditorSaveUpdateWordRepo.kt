package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.update

import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputMissingFieldException
import kotlin.jvm.Throws

interface LibraryDbEditorSaveUpdateWordRepo {
    @Throws(JaArDbInputMissingFieldException::class)
    suspend fun editorSaveEditedWord(
        id: Long,
        name: String,
        description: String?,
        languageCode: String,
        typeId: Long,
        categories: Set<Long>,
        meaningId: Long?,
    ): Boolean
}