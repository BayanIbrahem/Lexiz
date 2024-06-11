package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.labled_icon.JaArLabeledIcon
import kotlin.enums.EnumEntries

@Composable
internal fun <Step> EditorSteps(
    modifier: Modifier = Modifier,
    currentStep: Step,
    entries: EnumEntries<Step>,
    showAll: Boolean = true,
) where Step : EditorStepsData, Step : Enum<Step> {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        entries.forEach {
            if (showAll || it == currentStep) {
                JaArLabeledIcon(
                    data = it,
                    active = currentStep == it
                )
            }
        }
    }
}
