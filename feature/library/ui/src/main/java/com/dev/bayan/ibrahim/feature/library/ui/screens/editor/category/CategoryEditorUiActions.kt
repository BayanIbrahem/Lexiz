package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.category

import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorFieldUiAction

internal interface CategoryEditorUiActions {
    val nameFieldActions: EditorFieldUiAction
    val descriptionFieldActions: EditorFieldUiAction
    val wordFieldActions: EditorFieldUiAction

    fun onPreviousStep()
    fun onNextStep()
    fun onCancel()
    fun onConfirm()
}