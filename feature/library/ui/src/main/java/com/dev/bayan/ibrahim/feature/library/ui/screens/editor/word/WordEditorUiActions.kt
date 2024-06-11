package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.word

import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorFieldUiAction

internal interface WordEditorUiActions {
    val nameFieldActions: EditorFieldUiAction
    val descriptionFieldActions: EditorFieldUiAction
    val languageFieldActions: EditorFieldUiAction
    val typeFieldActions: EditorFieldUiAction
    val categoryFieldActions: EditorFieldUiAction
    val meaningFieldActions: EditorFieldUiAction

    fun onPreviousStep()
    fun onNextStep()
    fun onCancel()
    fun onConfirm()
}