package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field


import android.view.textclassifier.TextSelection
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.model.ModelItem
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.utils.EditorInputFieldValueType
import com.dev.bayan.ibrahim.feature.library.ui.theme.primarySurfaceContainer
import com.dev.bayan.ibrahim.feature.library.ui.theme.secondarySurfaceContainer
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditorField(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,

    data: EditorFieldUiState,
    hasSupportingText: Boolean,

    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,

    actions: EditorFieldUiAction,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.selectedValues?.let {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(it) { model ->
                    ElevatedFilterChip(
                        selected = false,
                        onClick = { actions.onRemoveFilterChip(model) },
                        label = { Text(text = model.value) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        },
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            containerColor = MaterialTheme.colorScheme.primarySurfaceContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    )
                }
            }
        }
        EditorFieldContent(
            focusManager = focusManager,
            data = data,
            hasSupportingText = hasSupportingText,
            onValueChange = actions::onValueChange,
            onSelectMatchingItem = actions::onSelectSuggestion,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditorFieldContent(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,

    data: EditorFieldUiState,
    hasSupportingText: Boolean,

    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,

    onValueChange: (String) -> Unit,
    onSelectMatchingItem: (ModelItem) -> Unit,
) {
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
    ) {
        data.input.title?.value?.let {
            Text(text = it, style = MaterialTheme.typography.titleMedium)
        }
        var textFieldValue by remember {
            mutableStateOf(TextFieldValue(data.value))
        }
        LaunchedEffect(key1 = data.value) {
            if (data.value != textFieldValue.text) {
                textFieldValue = TextFieldValue(data.value)
            }
        }
        ExposedDropdownMenuBox(
            modifier = Modifier,
            expanded = dropDownMenuExpanded && !data.suggestions.isNullOrEmpty(),
            onExpandedChange = {
                dropDownMenuExpanded = it
            }
        ) {
            OutlinedTextField(
                modifier = Modifier
                   .onFocusEvent {
                      dropDownMenuExpanded = it.isFocused
                   }
                   .menuAnchor()
                   .fillMaxWidth(),
                value = textFieldValue,
                isError = data.error != null,
                onValueChange = {
                    textFieldValue = it
                    onValueChange(it.text)
                },
                label = {
                    Text(
                        text = data.input.label.value,
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                    )
                },
                supportingText = if (hasSupportingText) {
                    { Text(text = (data.error ?: data.input.subtitle).value) }
                } else null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
            )
            data.suggestions?.let {
                LibraryEditorSuggestions(
                    modifier = Modifier.imePadding(),
                    visible = dropDownMenuExpanded,
                    items = it,
                    onSelectItem = {
                        onSelectMatchingItem(it)
                        dropDownMenuExpanded = false
                        focusManager.clearFocus(true)
                    },
                    onDismiss = {
                        dropDownMenuExpanded = false
                        focusManager.clearFocus(true)
                    },
                )
            }
        }
    }
}

@Composable
private fun LibraryEditorInputFieldIcon(
    modifier: Modifier = Modifier,
    type: EditorInputFieldValueType,
) {
    if (type != EditorInputFieldValueType.NONE) {
        Icon(
            modifier = modifier,
            painter = painterResource(id = type.iconRes),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdownMenuBoxScope.LibraryEditorSuggestions(
    modifier: Modifier = Modifier,
    visible: Boolean,
    items: PersistentList<ModelItem>,
    onSelectItem: (ModelItem) -> Unit,
    onDismiss: () -> Unit,
) {
    ExposedDropdownMenu(
        modifier = modifier
           .width(IntrinsicSize.Max)
           .background(MaterialTheme.colorScheme.primarySurfaceContainer),
        expanded = visible && items.isNotEmpty(),
        onDismissRequest = onDismiss,
    ) {
        items.forEach { it ->
            DropdownMenuItem(
                leadingIcon = it.languageCode?.let { { Text(text = it) } },
                text = {
                    Column {
                        Text(text = it.value)
                        if (!it.subtitle.isNullOrBlank()) {
                            Text(
                                text = it.subtitle!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 3,
                            )
                        }
                    }
                },
                onClick = { onSelectItem(it) },
            )
        }
    }
}

