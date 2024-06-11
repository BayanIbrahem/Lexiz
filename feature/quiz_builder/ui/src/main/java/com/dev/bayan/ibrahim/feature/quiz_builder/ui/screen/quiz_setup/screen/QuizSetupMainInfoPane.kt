package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.slider.JaArStepsSlider
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.fab.JaArFab
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalShrinkExitTransition
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.core.filter_groups.BuilderQuizFilterGroups
import com.dev.bayan.ibrahim.core.ui.components.input_field.JaArBasicInputField
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.core.language_selector.QuizLanguageSelector
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.fab_optoins.QuizFabOptions
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences.QuizTimePerWord
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.quiz_preferences.QuizWordsCount
import kotlinx.collections.immutable.PersistentList


@Composable
internal fun QuizSetupMainInfoPane(
    modifier: Modifier = Modifier,
    showTemplateGroupsList: Boolean,
    // ui state:
    uiState: QuizSetupUiState,

    // event:
    onEvent: (QuizSetupEvent) -> Unit,
    allLanguages: PersistentList<LanguageItem>,
) {
    val onFapOption: (QuizFabOptions) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnFabOption(it)) } }
    }
    val onNameChange: (String) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnNameValueChange(it)) } }
    }
    val onFirstLanguageSelect: (LanguageItem?) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnFirstLanguageSelectItem(it)) } }
    }
    val onSecondLanguageSelect: (LanguageItem?) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnSecondLanguageSelectItem(it)) } }
    }
    val onInvertSelectedLanguages: () -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnInvertSelectedLanguages) } }
    }
    val onTimeValueChange: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnTimeValueChange(QuizTimePerWord.entries[it])) } }
    }
    val onCountValueChange: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnCountValueChange(QuizWordsCount.entries[it])) } }
    }
    val onEditGroup: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnEditGroup(it)) } }
    }
    val onRemoveGroup: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnRemoveGroup(it)) } }
    }
    val onAddNewGroup: () -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnAddNewGroup) } }
    }
    val onAddTemplateGroup: () -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnAddTemplateGroup) } }
    }
    val name = uiState.name.value
    LaunchedEffect(key1 = uiState.name) {
        if (uiState.name.valueOrNull == null) {
            onEvent(QuizSetupEvent.OnNameValueChange(name))
        }
    }
    JaArContainer(
        modifier = modifier,
        type = JaArContainerType.PRIMARY,
        gradient = true,
        fab = {
            JaArFab(
                entries = QuizFabOptions.entries,
                containerType = JaArContainerType.PRIMARY,
                isScrollingUp = false,
                background = MaterialTheme.colorScheme.background,
                onClickOption = onFapOption
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            JaArBasicInputField(
                dividerModifier = Modifier.width(200.dp),
                value = uiState.name.value,
                onValueChange = onNameChange,
                prefix = JaArDynamicString.StrRes(R.string.quiz_name)
            )
            QuizLanguageSelector(
                languages = allLanguages,
                firstLanguage = uiState.firstLanguage,
                secondLanguage = uiState.secondLanguage,
                onFirstLanguageSelect = onFirstLanguageSelect,
                onSecondLanguageSelect = onSecondLanguageSelect,
                onInvert = onInvertSelectedLanguages,
            )
            JaArStepsSlider(
                modifier = Modifier.fillMaxWidth(),
                sliderValue = uiState.maxTimePerWord.ordinal,
                valuesLabels = QuizTimePerWord.asLabelsPersistentList(),
                title = JaArDynamicString.StrRes(R.string.quiz_preferences_wordTime_title),
                hint = JaArDynamicString.StrRes(R.string.quiz_preferences_wordTime_hint),
                onValueChange = onTimeValueChange,
            )
            JaArStepsSlider(
                modifier = Modifier.fillMaxWidth(),
                sliderValue = uiState.maxWordsCount.ordinal,
                valuesLabels = QuizWordsCount.asLabelsPersistentList(),
                title = JaArDynamicString.StrRes(R.string.quiz_preferences_wordsCount_title),
                hint = JaArDynamicString.StrRes(R.string.quiz_preferences_wordsCount_hint),
                onValueChange = onCountValueChange,
            )
            AnimatedVisibility(
                visible = showTemplateGroupsList,
                enter = fadeVerticalExpandEnterTransition(Alignment.Top),
                exit = fadeVerticalShrinkExitTransition(Alignment.Top),
            ) {
                BuilderQuizFilterGroups(
                    modifier = Modifier.fillMaxWidth(),
                    groups = uiState.groups,
                    onEditGroup = onEditGroup,
                    onRemoveGroup = onRemoveGroup,
                    onAddNewGroup = onAddNewGroup,
                    onAddTemplateGroup = onAddTemplateGroup,
                )
            }
        }
    }
}