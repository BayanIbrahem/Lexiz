package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field

import com.dev.bayan.ibrahim.core.common.model.ModelItem
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorFields
import kotlinx.collections.immutable.PersistentList

internal interface EditorFieldUiAction {
    fun onValueChange(newValue: String)
    fun onSelectSuggestion(suggestion: ModelItem)
    fun onRemoveFilterChip(model: ModelItem)
}
