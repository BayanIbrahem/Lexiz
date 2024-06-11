package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field

import com.dev.bayan.ibrahim.core.common.model.EditLanguageItem
import com.dev.bayan.ibrahim.core.common.model.ModelItem
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorFields
import kotlinx.collections.immutable.PersistentList

internal interface EditorFieldUiState {
    val id: Long?
    val input: EditorFields
    val value: String get() = ""
    val error: JaArDynamicString? get() = null
    val suggestions: PersistentList<ModelItem>? get() = null
    val selectedValues: PersistentList<ModelItem>? get() = null
}

internal interface MutableEditorFieldUiState : EditorFieldUiState {
    override var id: Long?
    override var value: String
    override var error: JaArDynamicString?
    override var suggestions: PersistentList<ModelItem>?
    override var selectedValues: PersistentList<ModelItem>?
}
