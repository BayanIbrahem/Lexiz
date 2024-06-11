package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.action_buttons


import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.row_column.JaArRowOrColumnIfNarrow
import java.lang.IllegalArgumentException

/**
 * editor screen steps next previous buttons (with current step)
 * for the first step previous button will not be available the same with next button on last step
 * @param currentStep current step
 * @param stepsCount steps count
 * @param onNext callback when click next button
 * @param onPrev callback when click previous button
 * @suppress indexing of steps here is from 1 and not from 0
 * @throws [IllegalArgumentException] when steps count is 0
 * @see EditorConfirmCancelActionButtons
 */
@Composable
internal fun EditorNextPrevActionButtons(
    modifier: Modifier = Modifier,
    currentStep: Int,
    stepsCount: Int,
    stepIsCompleted: Boolean,
    onPrev: () -> Unit,
    onNext: () -> Unit,
) {
    JaArRowOrColumnIfNarrow(
        modifier = modifier,
        spacedBetween = true,
        { // prev
            TextButton(
                onClick = onPrev,
                enabled = currentStep > 1,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            ) {
                Text(text = stringResource(id = R.string.back))
            }
        },
        { // text
            Text(
                text = stringResource(id = R.string.x_of_y, currentStep, stepsCount),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        { // next
            TextButton(
                onClick = onNext,
                enabled = (currentStep < stepsCount) && stepIsCompleted,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        },
    )
}
