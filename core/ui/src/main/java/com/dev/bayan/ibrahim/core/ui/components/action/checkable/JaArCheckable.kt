package com.dev.bayan.ibrahim.core.ui.components.action.checkable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction.Checkable.*
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString


/**
 * create a checkable item (check box, radio button, or switch) with text and optional supporting text
 * @param checked checked or not
 * @param enabled enabled or not
 * @param label one line text next to checkable item
 * @param supportingText extra optional text
 * @param type checkable item type see [JaArAction.Checkable]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JaArCheckable(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    label: JaArDynamicString,
    supportingText: JaArDynamicString? = null,
    style: JaArActionStyle = JaArActionStyle.PRIMARY,
    type: JaArAction.Checkable,
    onClick: (Boolean) -> Unit,
    onLongClick: (Boolean) -> Unit = {},
    onDupleClick: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .clip(RoundedCornerShape(CornerSize(24.dp)))
                .graphicsLayer { alpha = if (enabled) 1f else 0.38f }
                .combinedClickable(
                    enabled = enabled,
                    onClick = { onClick(checked) },
                    onLongClick = { onLongClick(checked) },
                    onDoubleClick = { onDupleClick(checked) },
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (type) {
                CHECK_BOX -> {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = null,
                        colors = CheckboxDefaults.colors(
                            checkedColor = style.color,
                            checkmarkColor = style.onColor,
                        )
                    )
                }

                RADIO_BUTTON -> {
                    RadioButton(
                        selected = checked,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = style.color,
                        )
                    )
                }

                SWITCH -> {
                    Switch(
                        modifier = Modifier
                            .size(32.dp, 16.dp)
                            .scale(0.66f),
                        checked = checked,
                        onCheckedChange = null,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = style.onColor,
                            checkedTrackColor = style.color,
                            disabledCheckedThumbColor = style.onColor,
                            disabledCheckedTrackColor = style.color,
                        )
                    )
                }
            }
            Text(
                modifier = Modifier,
                text = label.value,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1
            )
        }
        supportingText?.value?.let {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JaArCheckablePreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        var checked by remember {
            mutableStateOf(true)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            JaArCheckable(
                modifier = Modifier.weight(1f),
                checked = checked,
                label = JaArDynamicString.Str("label"),
                supportingText = JaArDynamicString.Str("supporting text"),
                type = JaArAction.Checkable.CHECK_BOX,
                onClick = { checked = !it }
            )
            JaArCheckable(
                modifier = Modifier.weight(1f),
                checked = checked,
                label = JaArDynamicString.Str("label"),
                supportingText = JaArDynamicString.Str("supporting text"),
                type = JaArAction.Checkable.RADIO_BUTTON,
                onClick = { checked = !it }
            )
            JaArCheckable(
                modifier = Modifier.weight(1f),
                checked = checked,
                label = JaArDynamicString.Str("label"),
                supportingText = JaArDynamicString.Str("supporting text"),
                type = JaArAction.Checkable.SWITCH,
                onClick = { checked = !it }
            )
        }

    }
}