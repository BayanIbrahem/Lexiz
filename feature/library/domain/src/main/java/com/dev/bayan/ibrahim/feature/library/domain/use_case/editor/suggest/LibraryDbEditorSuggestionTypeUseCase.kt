package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest

import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.suggestion.LibraryDbEditorSuggestionRepo
import com.dev.bayan.ibrahim.feature.library.domain.util.ignorabledLowercase
import com.dev.bayan.ibrahim.feature.library.domain.util.toSuggestionRegex
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class LibraryDbEditorSuggestionTypeUseCase @Inject constructor(
    private val repo: LibraryDbEditorSuggestionRepo
) {
    private var latestLanguageCode = ""
    private var allTypes: List<TypeEntity>? = null
    suspend operator fun invoke(
        typeName: String,
        languageCode: String,
        ignorableChars: Set<Char>,
    ): List<TypeItem> {
        if (allTypes == null || languageCode != latestLanguageCode) { // types set is not valid
            allTypes = repo.editorSuggestAllTypesOfLanguage(languageCode).firstOrNull() ?: listOf()
        }
        val regex = typeName.toSuggestionRegex(ignorableChars)
        return allTypes!!.mapNotNull {
            if (regex.matches(it.name.ignorabledLowercase(ignorableChars))) {
                TypeItem(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    languageCode = languageCode,
                )
            } else null
        }
    }
}