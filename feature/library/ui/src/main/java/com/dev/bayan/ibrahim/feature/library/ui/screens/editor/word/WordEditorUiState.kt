package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.word

import com.dev.bayan.ibrahim.core.common.model.ModelItem
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorFieldUiState
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorUiState
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.MutableEditorFieldUiState
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.WordEditorSteps
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorFieldUiAction
import kotlinx.collections.immutable.PersistentMap

internal interface WordEditorUiState : EditorUiState<WordEditorSteps> {
    val languageField: EditorFieldUiState
    val typeField: EditorFieldUiState
    val categoryField: EditorFieldUiState
    val meaningField: EditorFieldUiState
}

internal interface MutableWordEditorUiState : WordEditorUiState {
    override var id: Long?
    override var currentStep: WordEditorSteps
    override var steps: PersistentMap<WordEditorSteps, Boolean>
    override val nameField: MutableEditorFieldUiState
    override val descriptionField: MutableEditorFieldUiState
    override val languageField: MutableEditorFieldUiState
    override val typeField: MutableEditorFieldUiState
    override val categoryField: MutableEditorFieldUiState
    override val meaningField: MutableEditorFieldUiState
}
