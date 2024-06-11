package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.save_new

import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputWordCategoryException
import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputWordNameDescriptionException
import com.dev.bayan.ibrahim.core.common.jaar_exception.db.JaArDbInputMissingFieldException
import com.dev.bayan.ibrahim.core.common.model.SavableWordItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.save_new.LibraryDbEditorSaveNewWordRepo
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 * @throws JaArDbInputMissingFieldException
 * if name and description of the word are null [JaArDbInputWordNameDescriptionException]
 * or any category data is invalid [JaArDbInputWordCategoryException]
 */
class LibraryDbEditorSaveNewWordUseCase @Inject constructor(
    private val repo: LibraryDbEditorSaveNewWordRepo,
) {
    /**
     * @throws JaArDbInputMissingFieldException
     * if name and description of the word are null [JaArDbInputWordNameDescriptionException]
     * or any category data is invalid [JaArDbInputWordCategoryException]
     */
    @Throws(JaArDbInputMissingFieldException::class)
    suspend operator fun invoke(
        wordModel: SavableWordItem
    ): Long? {
        return repo.editorSaveNewWord(
            name = wordModel.name,
            description = wordModel.description,
            languageCode = wordModel.languageCode,
            categories = wordModel.categories,
            meaningId = wordModel.meaningId,
            typeId = wordModel.typeId
        )
    }

}