package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.category

import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorFieldUiState
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorUiState
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.MutableEditorFieldUiState
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.CategoryEditorSteps
import kotlinx.collections.immutable.PersistentMap

internal interface CategoryEditorUiState : EditorUiState<CategoryEditorSteps> {
    val wordField: EditorFieldUiState
}

internal interface MutableCategoryEditorUiState : CategoryEditorUiState {
    override var id: Long?
    override var currentStep: CategoryEditorSteps
    override var steps: PersistentMap<CategoryEditorSteps, Boolean>
    override val nameField: MutableEditorFieldUiState
    override val descriptionField: MutableEditorFieldUiState
    override val wordField: MutableEditorFieldUiState
}
