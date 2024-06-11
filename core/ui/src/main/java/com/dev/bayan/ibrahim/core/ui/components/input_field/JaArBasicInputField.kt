package com.dev.bayan.ibrahim.core.ui.components.input_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString


/**
 * must the resource string takes one parameter
 */
@Composable
fun JaArBasicInputField(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    dividerModifier: Modifier? = null,
    value: String,
    prefix: JaArDynamicString? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    onValueChange: (String) -> Unit,
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(value))
    }
    LaunchedEffect(key1 = value) {
        if (value != textFieldValue.text) {
            textFieldValue = TextFieldValue(value)
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            prefix?.value?.let {
                Text(
                    text = it,
                    style = textStyle,
                    fontWeight = FontWeight.Bold,
                )
            }
            BasicTextField(
                modifier = Modifier,
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onValueChange(it.text)
                },
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                minLines = 1,
                readOnly = readOnly,
                textStyle = textStyle,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        dividerModifier?.let { HorizontalDivider(modifier = it) }
    }
}