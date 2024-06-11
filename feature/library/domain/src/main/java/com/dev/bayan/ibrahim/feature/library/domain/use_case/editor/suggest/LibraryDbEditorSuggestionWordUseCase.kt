package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest

import com.dev.bayan.ibrahim.core.common.model.LanguageInterchangeables
import com.dev.bayan.ibrahim.core.common.model.WordItem
import com.dev.bayan.ibrahim.core.common.model.interchangeablesOf
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.suggestion.LibraryDbEditorSuggestionRepo
import com.dev.bayan.ibrahim.feature.library.domain.util.ignorabledLowercase
import com.dev.bayan.ibrahim.feature.library.domain.util.toSuggestionRegex
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LibraryDbEditorSuggestionWordUseCase @Inject constructor(
    private val repo: LibraryDbEditorSuggestionRepo
) {
    private var allWordsNames = repo.editorSuggestAllWordsWithNames()
    suspend operator fun invoke(
        wordName: String,
        existedIds: Set<Long> = emptySet(),
        ignorableChars: Set<Char>,
        interchangables: LanguageInterchangeables = interchangeablesOf()
    ): List<WordItem> {
        val regex = interchangables.mapToFirstOfGroup(wordName).toSuggestionRegex(ignorableChars)
        return allWordsNames.first().mapNotNull {
            if (it.id in existedIds) return@mapNotNull null

            if (
                regex.matches(
                    input = interchangables.mapToFirstOfGroup(
                        string = it.name
                    ).ignorabledLowercase(ignorableChars)
                )
            ) {
                WordItem(
                    id = it.id!!,
                    name = it.name,
                    description = it.description,
                    languageCode = it.language_code,
                )
            } else null
        }
    }
}