package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.word

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalShrinkExitTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalShrinkExitTransition
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorFields
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.action_buttons.EditorConfirmCancelActionButtons
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.action_buttons.EditorNextPrevActionButtons
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.EditorSteps
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.WordEditorSteps
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.WordEditorSteps.*
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorAnimatableField
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorField
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.title.EditorTitle
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.dev.bayan.ibrahim.core.ui.R as UiRes


@Composable
internal fun WordEditorScreen(
    modifier: Modifier = Modifier,
    uiState: WordEditorUiState,
    uiActions: WordEditorUiActions,
    screenSize: JaArScreenSize,
) {
    Row(
        modifier = modifier,
    ) {
    }
    if (screenSize.isCompat()) {
        FirstPane(uiState = uiState, uiActions = uiActions, screenSize = screenSize)
    } else {
        TwoPane(
            first = {
                FirstPane(uiState = uiState, uiActions = uiActions, screenSize = screenSize)
            },
            second = {
                SecondPane(uiState = uiState, uiActions = uiActions, screenSize = screenSize)
            },
            strategy = HorizontalTwoPaneStrategy(
                splitFraction = 0.5f,
                gapWidth = 16.dp
            ),
            displayFeatures = listOf(),
        )
    }
}

@Composable
private fun FirstPane(
    modifier: Modifier = Modifier,
    uiState: WordEditorUiState,
    uiActions: WordEditorUiActions,
    screenSize: JaArScreenSize,
) {
    JaArContainer(
        modifier = modifier.fillMaxSize(),
        type = JaArContainerType.PRIMARY
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            EditorTitle(
                title = JaArDynamicString.PluralRes(UiRes.plurals.word, 1),
                isShort = screenSize.isShort()
            )
            JaArContainer(
                type = JaArContainerType.SECONDARY
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EditorAnimatableField(
                        uiState = uiState.languageField,
                        uiActions = uiActions.languageFieldActions,
                        visible = uiState.currentStep == LANGUAGE,
                        isCompat = screenSize.isCompat(),
                    )
                    NameDescription(
                        currentStep = uiState.currentStep,
                        uiState = uiState,
                        uiActions = uiActions,
                        screenSize = screenSize,
                    )
                    EditorAnimatableField(
                        uiState = uiState.typeField,
                        uiActions = uiActions.typeFieldActions,
                        visible = uiState.currentStep == TYPE,
                        isCompat = screenSize.isCompat(),
                    )
                    EditorAnimatableField(
                        uiState = uiState.categoryField,
                        uiActions = uiActions.categoryFieldActions,
                        visible = uiState.currentStep == CATEGORY,
                        isCompat = screenSize.isCompat(),
                    )
                    EditorAnimatableField(
                        uiState = uiState.meaningField,
                        uiActions = uiActions.meaningFieldActions,
                        visible = uiState.currentStep == MEANING,
                        isCompat = screenSize.isCompat(),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    this@Column.AnimatedVisibility(
                        visible = screenSize.isCompat(),
                        enter = fadeHorizontalExpandEnterTransition(Alignment.Start),
                        exit = fadeHorizontalShrinkExitTransition(Alignment.Start),
                    ) {
                        ActionsColumn(
                            uiState = uiState,
                            uiActions = uiActions,
                            spacedBetween = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SecondPane(
    modifier: Modifier = Modifier,
    uiState: WordEditorUiState,
    uiActions: WordEditorUiActions,
    screenSize: JaArScreenSize,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        JaArContainer(type = JaArContainerType.PRIMARY) {
            EditorSteps(
                modifier = Modifier.fillMaxWidth(),
                currentStep = uiState.currentStep,
                entries = WordEditorSteps.entries,
                showAll = !screenSize.isShort()
            )
        }
        AnimatedVisibility(
            visible = !screenSize.isShort(),
            enter = fadeVerticalExpandEnterTransition(Alignment.Top),
            exit = fadeVerticalShrinkExitTransition(Alignment.Top),
        ) {
            JaArContainer(
                type = JaArContainerType.PRIMARY,
                gradient = true
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    when (uiState.currentStep) {
                        LANGUAGE -> listOf(uiState.languageField.error)
                        NAME_DESCRIPTION -> listOf(
                            uiState.nameField.error,
                            uiState.descriptionField.error
                        )

                        TYPE -> listOf(uiState.typeField.error)
                        CATEGORY -> listOf(uiState.categoryField.error)
                        MEANING -> listOf(uiState.meaningField.error)
                    }.mapIndexed { i, it ->
                        it ?: uiState.currentStep.fields[i].subtitle
                    }.forEach {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it.value,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = screenSize.isShort(),
        ) {
            JaArContainer(
                type = JaArContainerType.PRIMARY
            ) {
                ActionsColumn(
                    uiState = uiState,
                    uiActions = uiActions,
                    spacedBetween = true
                )
            }
        }
        AnimatedVisibility(
            visible = !screenSize.isShort(),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                JaArContainer(
                    type = JaArContainerType.PRIMARY
                ) {
                    EditorNextPrevActionButtons(
                        currentStep = uiState.currentStep.ordinal.inc(),
                        stepsCount = WordEditorSteps.entries.count(),
                        stepIsCompleted = uiState.steps[uiState.currentStep] == true,
                        onPrev = uiActions::onPreviousStep,
                        onNext = uiActions::onNextStep,
                    )
                }
                JaArContainer(
                    type = JaArContainerType.PRIMARY
                ) {
                    EditorConfirmCancelActionButtons(
                        confirmable = WordEditorSteps.entries.all { uiState.steps[it] == true },
                        spacedBetween = true,
                        onCancel = uiActions::onCancel,
                        onConfirm = uiActions::onConfirm,
                    )
                }
            }
        }
    }
}

@Composable
private fun NameDescription(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    currentStep: WordEditorSteps,
    uiState: WordEditorUiState,
    uiActions: WordEditorUiActions,
    screenSize: JaArScreenSize,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = currentStep == NAME_DESCRIPTION,
        enter = fadeHorizontalExpandEnterTransition(Alignment.Start),
        exit = fadeHorizontalShrinkExitTransition(Alignment.Start)
    ) {
        Column {
            EditorField(
                modifier = Modifier.fillMaxWidth(),
                data = uiState.nameField,
                hasSupportingText = screenSize.isCompat(),
                actions = uiActions.nameFieldActions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            EditorField(
                modifier = Modifier.fillMaxWidth(),
                data = uiState.descriptionField,
                hasSupportingText = screenSize.isCompat(),
                actions = uiActions.descriptionFieldActions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
    }
}

@Composable
private fun ActionsColumn(
    modifier: Modifier = Modifier,
    uiState: WordEditorUiState,
    uiActions: WordEditorUiActions,
    spacedBetween: Boolean,
) {
    Column(
        modifier = modifier
    ) {
        EditorNextPrevActionButtons(
            currentStep = uiState.currentStep.ordinal.inc(),
            stepsCount = WordEditorSteps.entries.count(),
            stepIsCompleted = uiState.steps[uiState.currentStep] == true,
            onPrev = uiActions::onPreviousStep,
            onNext = uiActions::onNextStep,
        )
        EditorConfirmCancelActionButtons(
            confirmable = WordEditorSteps.entries.all { uiState.steps[it] == true },
            spacedBetween = spacedBetween,
            onCancel = uiActions::onCancel,
            onConfirm = uiActions::onConfirm,
        )
    }
}
