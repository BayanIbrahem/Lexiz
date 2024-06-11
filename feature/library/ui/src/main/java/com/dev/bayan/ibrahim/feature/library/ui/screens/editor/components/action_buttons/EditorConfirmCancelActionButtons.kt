package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.action_buttons


import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.row_column.JaArRowOrColumnIfNarrow


/**
 * confirm and cancel buttons in screen editor in library feture
 * @param confirmable if confirm button is valid or not
 * @param spacedBetween if true then cancel button will be on the start and confirm will be on the end
 * other wise both will be on the end
 * @param onCancel when click cancel button
 * @param onConfirm when click confirm button
 * @see EditorNextPrevActionButtons
 */
@Composable
internal fun EditorConfirmCancelActionButtons(
    modifier: Modifier = Modifier,
    confirmable: Boolean,
    spacedBetween: Boolean,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        JaArRowOrColumnIfNarrow(
            modifier = modifier,
            spacedBetween = spacedBetween,
            {
                TextButton(
                    onClick = onCancel,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            {
                TextButton(
                    onClick = onConfirm,
                    enabled = confirmable,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                ) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }
        )
    }
}