package com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest

import android.util.Log
import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.suggestion.LibraryDbEditorSuggestionRepo
import com.dev.bayan.ibrahim.feature.library.domain.util.toSuggestionRegex
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LibraryDbEditorSuggestionCategoryUseCase @Inject constructor(
    repo: LibraryDbEditorSuggestionRepo,
) {
    private var allCategoriesFlow = repo.editorSuggestAllCategories()
    suspend operator fun invoke(
        categoryName: String,
        existedIds: Set<Long>,
    ): List<CategoryItem> {
        val regex = categoryName.toSuggestionRegex(setOf())
        return allCategoriesFlow.first().mapNotNull {
            if (it.id!! in existedIds) {
                null
            } else if (regex.matches(it.name.lowercase())) {
                CategoryItem(
                    id = it.id!!,
                    name = it.name,
                    description = it.description,
                )
            } else {
                null
            }
        }
    }
}