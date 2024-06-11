package com.dev.bayan.ibrahim.feature.library.domain.model.editor.init

import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.MeaningItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem

data class LibraryDbEditorInitWordModel(
    val id: Long,
    val name: String?,
    val description: String?,
    val language: LanguageItem,
    val type: TypeItem,
    val categories: List<CategoryItem>,
    val meaning: MeaningItem,
)
