package com.dev.bayan.ibrahim.core.ui.components.action.clicable


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction.Clickable
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction.Clickable.BUTTON
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction.Clickable.ICON_BUTTON
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction.Clickable.OUTLINED_BUTTON
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction.Clickable.TEXT_BUTTON
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector

/**
 * @param type type of the clickable see [JaArAction.Clickable]
 * @param label label showed for the clickable, (it will be ignored if the type was [ICON_BUTTON])
 * @param icon icon for the button (extra icon will be added for normal buttons, and it is required
 * for checkbox other wise it will lead an empty icon button)
 * @param filledIconButton if true then the icon button will have a background
 * @param style color style of the button,
 * @param enabled if enabled or not
 * @param tooltip tool tip on long click
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JaArClickable(
    modifier: Modifier = Modifier,
    type: Clickable,
    label: JaArDynamicString,
    icon: JaArDynamicVector? = null,
    filledIconButton: Boolean = false,
    style: JaArActionStyle = JaArActionStyle.PRIMARY,
    enabled: Boolean = true,
    tooltip: JaArDynamicString? = null,
    onClick: () -> Unit,
) {
    when (type) {
        BUTTON -> {
            Button(
                modifier = modifier,
                enabled = enabled,
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = style.color,
                    contentColor = style.onColor,
                    disabledContainerColor = style.color.copy(alpha = 0.38f),
                    disabledContentColor = style.onColor.copy(alpha = 0.38f)
                )
            ) {
                Text(text = label.value)
            }
        }

        OUTLINED_BUTTON -> {
            OutlinedButton(
                modifier = modifier,
                enabled = enabled,
                onClick = onClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = style.onColor,
                    disabledContentColor = style.onColor.copy(alpha = 0.38f)
                )
            ) {
                Text(text = label.value)
            }
        }

        TEXT_BUTTON -> {
            TextButton(
                modifier = modifier,
                enabled = enabled,
                onClick = onClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = style.onColor,
                    disabledContentColor = style.onColor.copy(alpha = 0.38f)
                )
            ) {
                Text(text = label.value)
            }
        }

        ICON_BUTTON -> {
            IconButton(
                modifier = modifier,
                enabled = enabled,
                onClick = onClick,
                colors = if (filledIconButton) {
                    IconButtonDefaults.filledIconButtonColors(
                        containerColor = style.color,
                        contentColor = style.onColor,
                        disabledContainerColor = style.color.copy(alpha = 0.38f),
                        disabledContentColor = style.onColor.copy(alpha = 0.38f)
                    )
                } else {
                    IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Unspecified,
                        contentColor = style.color,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = style.color.copy(alpha = 0.38f)
                    )
                },
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon.vector,
                        contentDescription = tooltip?.value
                    )
                }
            }
        }
    }
}
