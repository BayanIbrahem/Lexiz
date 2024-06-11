package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest

import com.dev.bayan.ibrahim.core.common.model.EditLanguageItem
import com.dev.bayan.ibrahim.core.common.model.LanguageInterchangeables
import com.dev.bayan.ibrahim.core.data.repo.JaArCurrentLocaleRepo
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.suggestion.LibraryDbEditorSuggestionRepo
import com.dev.bayan.ibrahim.feature.library.domain.util.toSuggestionRegex
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class LibraryDbEditorSuggestionLanguageUseCase @Inject constructor(
    private val repo: LibraryDbEditorSuggestionRepo,
    private val locale: JaArCurrentLocaleRepo,
) {
    private var allLanguages: List<LanguageEntity>? = null
    suspend operator fun invoke(languageName: String): List<EditLanguageItem> {
        if (allLanguages == null) {
            allLanguages = repo.editorSuggestAllLanguages().firstOrNull() ?: listOf()
        }
        val regex = languageName.toSuggestionRegex()
        return allLanguages!!.mapNotNull {
            val selfName = locale.getLanguageSelfDisplayName(it.language_code)
            val currentName = locale.getLanguageLocalDisplayName(it.language_code)
            if (
                regex.matches(selfName.lowercase()) ||
                regex.matches(currentName.lowercase()) ||
                regex.matches(it.language_code)
            ) {
                EditLanguageItem(
                    languageCode = it.language_code,
                    localDisplayName = currentName,
                    selfDisplayName = selfName,
                    interchangeables = LanguageInterchangeables(it.interchangable_characters_groups),
                    validChars = it.valid_characters.toSet(),
                    ignorables = it.search_ignorable_characters.toSet(),
                )
            } else null
        }
    }
}