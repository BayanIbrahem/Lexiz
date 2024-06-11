package com.dev.bayan.ibrahim.core.ui.components.action.selectable.drop_down

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectable
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun <Selectable> JaArDropDownMenu(
    modifier: Modifier = Modifier,
    options: PersistentList<Selectable>,
    selected: Selectable?,
    onSelectItem: (Selectable, Boolean) -> Unit,
    show: Boolean,
    optionEnability: (Selectable) -> Boolean,
    onDismiss: () -> Unit,
) where Selectable : JaArSelectable {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()
    DropdownMenu(
        modifier = modifier,
        expanded = show,
        onDismissRequest = onDismiss,
    ) {
        options.forEach { it ->
            PlainTooltipBox(
                tooltip = { PlainTooltip { Text(text = it.tooltip?.value ?: "") } }
            ) {
                DropdownMenuItem(
                    modifier = Modifier.combinedClickable(
                        onClick = { onSelectItem(it, it == selected); onDismiss() },
                        onLongClick = it.tooltip?.let {
                            { coroutineScope.launch { tooltipState.show() } }
                        }
                    ),
                    enabled = optionEnability(it),
                    text = {
                        Column {
                            Text(text = it.label.value, style = MaterialTheme.typography.labelLarge)
                            (if (optionEnability(it)) {
                                it.supportingText?.value
                            } else {
                                it.disabledSupportingText?.value
                            })?.let {
                                Text(
                                    modifier = Modifier.alpha(0.75f),
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        }
                    },
                    onClick = { onSelectItem(it, it == selected); onDismiss() },
                    leadingIcon = it.leadingIcon?.let {
                        { Icon(imageVector = it.vector, contentDescription = null) }
                    },
                    trailingIcon = (if (selected == it) Icons.Filled.Check else it.trailingIcon?.vector)?.let {
                        { Icon(imageVector = it, contentDescription = null) }
                    },
                )
            }
        }
    }
}
