package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.update

import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputWordCategoryException
import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputWordNameDescriptionException
import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputMissingFieldException
import com.dev.bayan.ibrahim.core.common.model.SavableWordItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.update.LibraryDbEditorSaveUpdateWordRepo
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 * @throws JaArDbInputMissingFieldException
 * if name and description of the word are null [JaArDbInputWordNameDescriptionException]
 * or any category data is invalid [JaArDbInputWordCategoryException]
 */
class LibraryDbEditorSaveUpdateWordUseCase @Inject constructor(
    private val repo: LibraryDbEditorSaveUpdateWordRepo
) {
    /**
     * @throws JaArDbInputMissingFieldException
     * if name and description of the word are null [JaArDbInputWordNameDescriptionException]
     * or any category data is invalid [JaArDbInputWordCategoryException]
     */
    @Throws(JaArDbInputMissingFieldException::class)
    suspend operator fun invoke(
        model: SavableWordItem,
    ): Boolean {

        return repo.editorSaveEditedWord(
            id = model.id,
            name = model.name,
            description = model.description,
            languageCode = model.languageCode,
            categories = model.categories,
            meaningId = model.meaningId,
            typeId = model.typeId
        )
    }
}
