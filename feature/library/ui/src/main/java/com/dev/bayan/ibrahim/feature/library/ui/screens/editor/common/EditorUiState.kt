package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common

import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.EditorStepsData
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorFieldUiState

internal interface EditorUiState<Step> where Step : EditorStepsData, Step : Enum<Step> {
    val id: Long?
    val currentStep: Step
    val steps: Map<Step, Boolean>
    val nameField: EditorFieldUiState
    val descriptionField: EditorFieldUiState
}