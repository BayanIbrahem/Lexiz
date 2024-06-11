package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest

import com.dev.bayan.ibrahim.core.common.model.MeaningItem
import com.dev.bayan.ibrahim.core.common.model.WordItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.suggestion.LibraryDbEditorSuggestionRepo
import com.dev.bayan.ibrahim.feature.library.domain.util.toSuggestionRegex
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LibraryDbEditorSuggestionMeaningUseCase @Inject constructor(
    repo: LibraryDbEditorSuggestionRepo,
) {
    private var allWordsNames = repo.editorSuggestAllWordsWithNames()
    suspend operator fun invoke(wordName: String): List<MeaningItem> {
        val regex = wordName.toSuggestionRegex()
        return allWordsNames.first().mapNotNull {
            if (regex.matches(it.name.lowercase())) {
                MeaningItem(
                    id = it.meaningId,
                    wordName = it.name,
                    languageCode = it.language_code,
                )
            } else null
        }
    }
}