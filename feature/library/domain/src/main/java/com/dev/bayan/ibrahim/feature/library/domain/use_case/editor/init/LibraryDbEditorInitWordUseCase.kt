package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.init

import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.MeaningItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.core.data.repo.JaArCurrentLocaleRepo
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.init.LibraryDbEditorInitWordRepo
import com.dev.bayan.ibrahim.feature.library.domain.model.editor.init.LibraryDbEditorInitWordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LibraryDbEditorInitWordUseCase @Inject constructor(
    private val repo: LibraryDbEditorInitWordRepo,
    private val locale: JaArCurrentLocaleRepo,
) {
    suspend operator fun invoke(wordId: Long): LibraryDbEditorInitWordModel? {
        val wordWithCategories = withContext(Dispatchers.IO) {
            async {
                repo.editorGetWordWithCategories(wordId).first()
            }
        }
        val type = withContext(Dispatchers.IO) {
            async {
                repo.editorGetWordType(wordId).first()
            }
        }
        val meaningFirstWord = withContext(Dispatchers.IO) {
            async {
                repo.editorGetWordMeaningFirstWord(wordId).first()
            }
        }
        return wordWithCategories.await()?.let { (word, categories) ->
            LibraryDbEditorInitWordModel(
                id = wordId,
                name = word.name,
                description = word.description,
                language = LanguageItem(
                    languageCode = word.language_code,
                    selfDisplayName = locale.getLanguageSelfDisplayName(word.language_code),
                    localDisplayName = locale.getLanguageLocalDisplayName(word.language_code),
                ),
                type = TypeItem(
                    id = type.await().id,
                    languageCode = word.language_code,
                    name = type.await().name,
                    description = type.await().description
                ),
                categories = categories.map {
                    CategoryItem(
                        id = it.id!!,
                        name = it.name,
                        description = it.description
                    )
                },
                meaning = MeaningItem(
                    id = word.meaningId,
                    wordName = meaningFirstWord.await()?.name ?: "",
                    languageCode = meaningFirstWord.await()?.language_code ?: "",
                )
            )
        }
//        return combine(
//            repo.editorGetWordWithCategories(wordId),
//            repo.editorGetWordType(wordId),
//            repo.editorGetWordMeaningFirstWord(wordId)
//        ) { (relation, t, meaning) ->
//            val wordWithCategory = (relation as WordWithCategoriesRelation?) ?: return@combine null
//            val type = t as TypeEntity
//            val meaningFirstWord  = meaning as String?
//            return@combine LibraryDbEditorInitWordModel(
//                id = wordId,
//                name = wordWithCategory.word.name,
//                description = wordWithCategory.word.description,
//                languageDisplayName = locale.getLanguageSelfDisplayName(wordWithCategory.word.language_code),
//                languageCode = wordWithCategory.word.language_code,
//                categories = wordWithCategory.categories.associate {
//                    it.id!! to (it.name to it.description)
//                },
//                meaningId = wordWithCategory.word.meaningId,
//                meaningFirstWord = meaningFirstWord ?: wordWithCategory.word.name ?: "",
//                typeId = type.id,
//                typeName = type.name,
//                typeDescription = type.description
//            )
//        }
    }
}