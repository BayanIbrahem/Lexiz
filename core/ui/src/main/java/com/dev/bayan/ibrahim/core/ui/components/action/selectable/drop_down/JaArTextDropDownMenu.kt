package com.dev.bayan.ibrahim.core.ui.components.action.selectable.drop_down

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectable
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectableHeader
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectableItem
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlin.random.Random


/**
 * in icon button that shows a drop down menu on click
 */
@Composable
fun <Selectable> JaArTextDropDownMenu(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = false,
    selected: Selectable?,
    options: PersistentList<Selectable>,
    defaultLabel: JaArDynamicString,
    title: JaArDynamicString? = null,
    leadingIcon: JaArDynamicVector? = null,
    supportingText: JaArDynamicString? = null,
    onSelectItem: (Selectable, Boolean) -> Unit,
    optionEnability: (Selectable) -> Boolean = { true },
) where Selectable : JaArSelectable {
    var show by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier,
    ) {
        JaArSelectableHeader(
            title = title,
            fillMaxWidth = fillMaxWidth,
            leadingIcon = leadingIcon,
            label = selected?.label ?: defaultLabel,
            trailingIcon = JaArDynamicVector.Vector(
                value = if (show) {
                    Icons.Filled.ArrowDropDown
                } else {
                    Icons.AutoMirrored.Filled.ArrowRight
                }
            ),
            supportingText = supportingText,
            onClick = { show = true }
        )
        JaArDropDownMenu(
            options = options,
            selected = selected,
            onSelectItem = onSelectItem,
            show = show,
            optionEnability = optionEnability
        ) { show = false }
    }
}

@Preview(showBackground = true)
@Composable
private fun TextDropDownPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val options = persistentListOf(
            JaArSelectableItem(
                JaArDynamicString.Str("option 1"),
                supportingText = JaArDynamicString.Str("option supporting text 1")
            ),
            JaArSelectableItem(
                JaArDynamicString.Str("option 2"),
                supportingText = JaArDynamicString.Str("option supporting text 2")
            ),
            JaArSelectableItem(
                JaArDynamicString.Str("option 3"),
                supportingText = JaArDynamicString.Str("option supporting text 3")
            ),
            JaArSelectableItem(
                JaArDynamicString.Str("option 4"),
                supportingText = JaArDynamicString.Str("option supporting text 4")
            ),
        )
        var selected by remember {
            mutableStateOf(options.first())
        }

        JaArTextDropDownMenu(
            fillMaxWidth = true,
            selected = selected,
            options = options,
            defaultLabel = JaArDynamicString.Str("default label"),
            onSelectItem = { it, _ -> selected = it },
            supportingText = JaArDynamicString.Str("supporting text"),
            leadingIcon = JaArDynamicVector.Vector(Icons.Filled.Home),
            optionEnability = {
                Random.nextBoolean()
            }
        )
    }
}