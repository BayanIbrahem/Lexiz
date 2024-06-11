package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.save_new

import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputMissingFieldException
import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbEditorRepo
import kotlin.jvm.Throws

/**
 * this repo provides functionalities for saving or editing existed words
 *
 * - [editorSaveNewWord]
 *
 * @see LibraryDbEditorRepo
 *
 * @see LibraryDbEditorSaveNewCategoryRepo
 */
interface LibraryDbEditorSaveNewWordRepo {
    /**
     * save a new word:
     * @return id of the word when saved or null if not saved
     * @param name name of the word
     * @param description description of the word
     * @param languageCode code of the language,
     */
    @Throws(JaArDbInputMissingFieldException::class)
    suspend fun editorSaveNewWord(
        name: String,
        description: String?,
        languageCode: String,
        typeId: Long,
        categories: Set<Long>,
        meaningId: Long?,
    ): Long?
}